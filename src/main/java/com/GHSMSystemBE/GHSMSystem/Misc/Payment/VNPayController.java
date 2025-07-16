package com.GHSMSystemBE.GHSMSystem.Misc.Payment;

import com.GHSMSystemBE.GHSMSystem.Misc.Payment.DTOs.PaymentRequestDTO;
import com.GHSMSystemBE.GHSMSystem.Misc.Payment.DTOs.PaymentResponseDTO;
import com.GHSMSystemBE.GHSMSystem.Misc.Payment.DTOs.TransactionResponseDTO;
import com.GHSMSystemBE.GHSMSystem.Misc.QRCodeGen.QRCodeGenerator;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Services.IBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/payment")
@CrossOrigin("*")
@Tag(name = "VNPay Payment", description = "API endpoints for VNPay payment processing")
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private IBookingService bookingService;
    @Autowired
    private TransactionRepo transactionRepository;

    @Operation(summary = "Create VNPay QR code for payment",
            description = "Generates a QR code containing the VNPay payment URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "QR code generated successfully",
                    content = @Content(mediaType = "image/png")),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/vnpay/create-qr", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<BufferedImage> createVNPayQR(
            @RequestBody PaymentRequestDTO paymentRequest,
            HttpServletRequest request) {
        try {
            // Validate required parameters
            if (paymentRequest.getUserId() == null || paymentRequest.getAmount() == null) {
                return ResponseEntity.badRequest().build();
            }

            // Get client IP address
            String ipAddress = request.getRemoteAddr();

            // Generate unique order ID
            String orderId = "GHSM_" + System.currentTimeMillis();

            // Log payment request for debugging
            System.out.println("Creating payment for user: " + paymentRequest.getUserId() + ", amount: " + paymentRequest.getAmount());

            // Create VNPay payment URL
            String paymentUrl = vnPayService.createPaymentUrl(
                    orderId,
                    paymentRequest.getAmount(),
                    paymentRequest.getOrderInfo(),
                    ipAddress);

            // Save transaction record
            Transaction transaction = new Transaction();
            transaction.setOrderId(orderId);
            transaction.setAmount(new BigDecimal(paymentRequest.getAmount()));
            transaction.setOrderInfo(paymentRequest.getOrderInfo());
            transaction.setStatus("PENDING");
            transaction.setUserId(paymentRequest.getUserId());
            transaction.setCreatedAt(LocalDateTime.now());
            transactionRepository.save(transaction);

            // Generate QR code
            BufferedImage qrCode = QRCodeGenerator.generateQRCode(paymentUrl);

            return ResponseEntity.ok()
                    .header("X-Order-Id", orderId)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrCode);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get transaction status",
            description = "Retrieves the status of a payment transaction by order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction status retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Transaction not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/vnpay/status/{orderId}")
    @ResponseBody
    public ResponseEntity<TransactionResponseDTO> getTransactionStatus(@PathVariable String orderId) {

        TransactionResponseDTO response = new TransactionResponseDTO();

        try {
            Transaction transaction = transactionRepository.findById(orderId).orElse(null);

            if (transaction != null) {
                response.setStatus("success");
                response.setTransactionStatus(transaction.getStatus());
                response.setOrderId(transaction.getOrderId());
                response.setAmount(transaction.getAmount());
                response.setCreateDate(transaction.getCreatedAt());
                response.setTransactionId(transaction.getTransactionId());
                response.setResultCode(transaction.getResultCode());
                return ResponseEntity.ok(response);
            } else {
                response.setStatus("error");
                response.setMessage("Transaction not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatus("error");
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Handle VNPay browser redirect",
            description = "Processes user browser redirect from VNPay after payment and shows result page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirect to payment complete page")
    })
    @GetMapping("/vnpay-return-view")
    public String vnpayReturnView(@RequestParam Map<String, String> params, Model model) {
        System.out.println("\n========== VNPAY BROWSER RETURN ==========");
        System.out.println("Time: " + new Date());
        System.out.println("All parameters:");
        params.forEach((key, value) -> System.out.println(key + " = " + value));

        try {
            String orderId = params.get("vnp_TxnRef");
            String responseCode = params.get("vnp_ResponseCode");
            String transactionNo = params.get("vnp_TransactionNo");
            String amountStr = params.get("vnp_Amount");
            boolean success = "00".equals(responseCode);

            System.out.println("Order ID: " + orderId);
            System.out.println("Response Code: " + responseCode);
            System.out.println("Success: " + success);

            String amount = "0";
            if (amountStr != null && !amountStr.isEmpty()) {
                BigDecimal amountDecimal = new BigDecimal(amountStr).divide(new BigDecimal("100"));
                amount = amountDecimal.toString();
            }

            if (orderId != null && !orderId.isEmpty()) {
                try {
                    Transaction transaction = transactionRepository.findById(orderId).orElse(null);
                    if (transaction != null) {
                        System.out.println("Found transaction: " + transaction.getOrderId());
                        transaction.setTransactionId(transactionNo);
                        transaction.setResultCode(responseCode);
                        transaction.setStatus(success ? "SUCCESS" : "FAILED");
                        transactionRepository.save(transaction);
                        System.out.println("Transaction updated: " + transaction.getStatus());

                        // Update booking status
                        String appointmentId = transaction.getAppointmentId();

                        // If appointmentId is null, try to extract from orderId
                        if (appointmentId == null && orderId.contains("BOOKING_")) {
                            try {
                                String[] parts = orderId.split("BOOKING_")[1].split("_");
                                if (parts.length > 0) {
                                    appointmentId = parts[0];
                                    System.out.println("Extracted appointment ID: " + appointmentId);
                                }
                            } catch (Exception e) {
                                System.err.println("Error extracting appointment ID: " + e.getMessage());
                            }
                        }

                        if (appointmentId != null && !appointmentId.isEmpty()) {
                            try {
                                ServiceBooking booking = bookingService.getById(appointmentId);
                                if (booking != null) {
                                    System.out.println("Found booking: " + booking.getId());
                                    booking.setTransactionId(transactionNo);
                                    booking.setPaymentStatus(success ? "PAID" : "PAYMENT_FAILED");

                                    ServiceBooking updated = bookingService.updateBooking(booking);
                                    System.out.println("Booking updated successfully: " + updated.getPaymentStatus());
                                } else {
                                    System.err.println("Booking not found: " + appointmentId);
                                }
                            } catch (Exception e) {
                                System.err.println("Error updating booking: " + e.getMessage());
                                e.printStackTrace();
                            }
                        } else {
                            System.err.println("No appointment ID found in transaction or order ID");
                        }
                    } else {
                        System.err.println("Transaction not found: " + orderId);
                    }
                } catch (Exception e) {
                    System.err.println("Error updating transaction: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            System.out.println("========== END VNPAY BROWSER RETURN ==========\n");

            return "redirect:/payment-complete.html?success=" + success +
                    "&orderId=" + (orderId != null ? orderId : "") +
                    "&amount=" + amount +
                    "&txnId=" + (transactionNo != null ? transactionNo : "");
        } catch (Exception e) {
            System.err.println("Error in vnpayReturnView: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/payment-complete.html?success=false&error=" + e.getMessage();
        }
    }


    @Operation(summary = "Create VNPay QR code for booking payment",
            description = "Generates a QR code containing the VNPay payment URL for a specific booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "QR code generated successfully",
                    content = @Content(mediaType = "image/png")),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters or booking already paid"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/vnpay/booking/{bookingId}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<BufferedImage> createBookingPayment(
            @PathVariable String bookingId,
            @RequestParam(required = false) String userId,
            HttpServletRequest request) {
        try {
            ServiceBooking booking = bookingService.getById(bookingId);
            if (booking == null) {
                System.err.println("Booking not found: " + bookingId);
                return ResponseEntity.notFound().build();
            }

            if ("PAID".equals(booking.getPaymentStatus())) {
                System.err.println("Booking already paid: " + bookingId);
                return ResponseEntity.badRequest().build();
            }

            String payingUserId = userId != null ? userId : booking.getCustomerId().getId().toString();
            String ipAddress = request.getRemoteAddr();
            String orderId = "GHSM_BOOKING_" + bookingId + "_" + System.currentTimeMillis();

            BigDecimal amount = BigDecimal.valueOf(booking.getServiceTypeId().getPrice());
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.err.println("Invalid amount for booking: " + bookingId);
                return ResponseEntity.badRequest().build();
            }

            String orderInfo = "Payment for " + booking.getServiceTypeId().getName() +
                    " appointment on " + booking.getAppointmentDate().toString();

            String paymentUrl = vnPayService.createPaymentUrl(orderId, amount.toString(), orderInfo, ipAddress);

            // Save transaction with proper appointment ID
            Transaction transaction = new Transaction();
            transaction.setOrderId(orderId);
            transaction.setAmount(amount);
            transaction.setOrderInfo(orderInfo);
            transaction.setStatus("PENDING");
            transaction.setUserId(payingUserId);
            transaction.setCreatedAt(LocalDateTime.now());
            transaction.setAppointmentId(bookingId); // CRITICAL: Set appointment ID
            transactionRepository.save(transaction);

            // Update booking
            booking.setPaymentUrl(paymentUrl);
            booking.setPaymentStatus("PENDING");
            booking.setTransactionId(orderId);
            bookingService.updateBooking(booking);

            BufferedImage qrCode = QRCodeGenerator.generateQRCode(paymentUrl);

            return ResponseEntity.ok()
                    .header("X-Order-Id", orderId)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrCode);

        } catch (Exception e) {
            System.err.println("Error creating booking payment: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get payment QR code for an existing booking",
            description = "Retrieves the QR code image for an existing booking's payment URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "QR code retrieved successfully",
                    content = @Content(mediaType = "image/png")),
            @ApiResponse(responseCode = "400", description = "No payment URL available for booking"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/booking/{bookingId}/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<BufferedImage> getBookingQRCode(
            @PathVariable @Schema(description = "ID of the booking to get QR code for") String bookingId) {
        try {
            // Fetch booking details
            ServiceBooking booking = bookingService.getById(bookingId);
            if (booking == null) {
                return ResponseEntity.notFound().build();
            }

            // Check if payment URL exists
            String paymentUrl = booking.getPaymentUrl();
            if (paymentUrl == null || paymentUrl.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            // Generate QR code
            BufferedImage qrCode = QRCodeGenerator.generateQRCode(paymentUrl);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrCode);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}