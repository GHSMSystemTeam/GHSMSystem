package com.GHSMSystemBE.GHSMSystem.Misc.Payment;

import com.GHSMSystemBE.GHSMSystem.Misc.Payment.DTOs.PaymentRequestDTO;
import com.GHSMSystemBE.GHSMSystem.Misc.Payment.DTOs.PaymentResponseDTO;
import com.GHSMSystemBE.GHSMSystem.Misc.Payment.DTOs.TransactionResponseDTO;
import com.GHSMSystemBE.GHSMSystem.Misc.QRCodeGen.QRCodeGenerator;
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
import java.util.Map;

@Controller
@RequestMapping("/payment")
@CrossOrigin("*")
@Tag(name = "VNPay Payment", description = "API endpoints for VNPay payment processing")
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;

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

    @Operation(summary = "Handle VNPay payment callback",
            description = "Processes the callback from VNPay after payment completion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid payment data"),
            @ApiResponse(responseCode = "404", description = "Transaction not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/vnpay/return")
    @ResponseBody
    public ResponseEntity<PaymentResponseDTO> vnpayReturn(@RequestParam Map<String, String> params) {
        // Your existing code with updated return type...
        System.out.println("VNPay Return - Received parameters: " + params);

        PaymentResponseDTO response = new PaymentResponseDTO();

        try {
            if (params == null || params.isEmpty()) {
                System.err.println("ERROR: No parameters received from VNPay");
                response.setStatus("error");
                response.setMessage("No parameters received from payment gateway");
                return ResponseEntity.badRequest().body(response);
            }

            String orderId = params.get("vnp_TxnRef");
            if (orderId == null) {
                orderId = params.get("vnp_OrderInfo");
            }

            if (orderId == null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (entry.getValue() != null && entry.getValue().startsWith("GHSM_")) {
                        orderId = entry.getValue();
                        System.out.println("Found order ID in alternate parameter: " + entry.getKey());
                        break;
                    }
                }
            }

            if (orderId == null || orderId.trim().isEmpty()) {
                System.err.println("ERROR: Invalid or missing order ID in VNPay response");
                response.setStatus("error");
                response.setMessage("Invalid order ID in payment response");
                return ResponseEntity.badRequest().body(response);
            }

            String responseCode = params.get("vnp_ResponseCode");
            String transactionNo = params.get("vnp_TransactionNo");

            System.out.println("Processing payment return - Order ID: " + orderId + ", Response Code: " + responseCode);

            try {
                Transaction transaction = transactionRepository.findById(orderId).orElse(null);

                if (transaction != null) {
                    transaction.setTransactionId(transactionNo);

                    if ("00".equals(responseCode)) {
                        transaction.setStatus("SUCCESS");
                        response.setStatus("success");
                        response.setMessage("Payment successful");
                    } else {
                        transaction.setStatus("FAILED");
                        transaction.setResultCode(responseCode);
                        response.setStatus("failed");
                        response.setMessage("Payment failed with code: " + responseCode);
                    }

                    transactionRepository.save(transaction);
                    return ResponseEntity.ok(response);
                } else {
                    System.err.println("Transaction not found in database: " + orderId);
                    response.setStatus("error");
                    response.setMessage("Transaction not found: " + orderId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } catch (Exception dbEx) {
                System.err.println("Database error: " + dbEx.getMessage());
                response.setStatus("error");
                response.setMessage("Database error: " + dbEx.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus("error");
            response.setMessage("An error occurred processing the payment response: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
        // Your existing code with updated return type...
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
    @GetMapping("/vnpay-return")
    public String vnpayReturnView(@RequestParam Map<String, String> params, Model model) {
        System.out.println("VNPay browser return with params: " + params);

        try {
            // Get key parameters
            String orderId = params.get("vnp_TxnRef");
            String responseCode = params.get("vnp_ResponseCode");
            String transactionNo = params.get("vnp_TransactionNo");
            String amountStr = params.get("vnp_Amount");
            boolean success = "00".equals(responseCode);

            // Format amount if available
            String amount = "0";
            if (amountStr != null && !amountStr.isEmpty()) {
                BigDecimal amountDecimal = new BigDecimal(amountStr).divide(new BigDecimal("100"));
                amount = amountDecimal.toString();
            }

            // If order ID exists, update the transaction in the database
            if (orderId != null && !orderId.isEmpty()) {
                try {
                    Transaction transaction = transactionRepository.findById(orderId).orElse(null);
                    if (transaction != null) {
                        transaction.setTransactionId(transactionNo);
                        transaction.setResultCode(responseCode);

                        if (success) {
                            transaction.setStatus("SUCCESS");
                        } else {
                            transaction.setStatus("FAILED");
                        }

                        transactionRepository.save(transaction);
                    }
                } catch (Exception e) {
                    System.err.println("Error updating transaction: " + e.getMessage());
                    // Continue with the redirect even if database update fails
                }
            }

            // Redirect to the static HTML page with query parameters
            return "redirect:/payment-complete.html?success=" + success +
                    "&orderId=" + (orderId != null ? orderId : "") +
                    "&amount=" + amount +
                    "&txnId=" + (transactionNo != null ? transactionNo : "");
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/payment-complete.html?success=false&error=" + e.getMessage();
        }
    }
}