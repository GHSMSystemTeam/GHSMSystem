package com.GHSMSystemBE.GHSMSystem.Misc.QRCodePayment.Payment;

import com.GHSMSystemBE.GHSMSystem.Misc.QRCodePayment.QRCodeGen.QRCodeGenerator;
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
import java.util.HashMap;
import java.util.Map;

@Controller  // Change to Controller instead of RestController for HTML views
@RequestMapping("/payment")
@CrossOrigin("*")
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private TransactionRepo transactionRepository;

    // Keep your existing endpoints
    @PostMapping(value = "/vnpay/create-qr", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody  // Need this when mixing JSON and HTML responses in a @Controller
    public ResponseEntity<BufferedImage> createVNPayQR(
            @RequestBody Map<String, String> paymentRequest,
            HttpServletRequest request) {
        try {
            // Validate required parameters
            String userId = paymentRequest.get("userId");
            String amount = paymentRequest.get("amount");
            String orderInfo = paymentRequest.get("orderInfo");

            if (userId == null || amount == null) {
                return ResponseEntity.badRequest().build();
            }

            // Get client IP address
            String ipAddress = request.getRemoteAddr();

            // Generate unique order ID
            String orderId = "GHSM_" + System.currentTimeMillis();

            // Log payment request for debugging
            System.out.println("Creating payment for user: " + userId + ", amount: " + amount);

            // Create VNPay payment URL
            String paymentUrl = vnPayService.createPaymentUrl(orderId, amount, orderInfo, ipAddress);

            // Save transaction record
            Transaction transaction = new Transaction();
            transaction.setOrderId(orderId);
            transaction.setAmount(new BigDecimal(amount));
            transaction.setOrderInfo(orderInfo);
            transaction.setStatus("PENDING");
            transaction.setUserId(userId);
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

    @GetMapping("/vnpay/return")
    @ResponseBody
    public ResponseEntity<Map<String, String>> vnpayReturn(@RequestParam Map<String, String> params) {
        // Your existing code...
        // No changes needed here
        Map<String, String> response = new HashMap<>();

        // DEBUG: Log all incoming parameters
        System.out.println("VNPay Return - Received parameters: " + params);

        try {
            // First check if we have any parameters at all
            if (params == null || params.isEmpty()) {
                System.err.println("ERROR: No parameters received from VNPay");
                response.put("status", "error");
                response.put("message", "No parameters received from payment gateway");
                return ResponseEntity.badRequest().body(response);
            }

            // Get order ID from parameters - check both standard and alternate parameter names
            String orderId = params.get("vnp_TxnRef");
            if (orderId == null) {
                // Try alternate parameter names VNPay might use
                orderId = params.get("vnp_OrderInfo");
            }

            // If still null, check if there's any parameter that contains the GHSM_ prefix
            if (orderId == null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (entry.getValue() != null && entry.getValue().startsWith("GHSM_")) {
                        orderId = entry.getValue();
                        System.out.println("Found order ID in alternate parameter: " + entry.getKey());
                        break;
                    }
                }
            }

            // Check if order ID is valid after our best efforts
            if (orderId == null || orderId.trim().isEmpty()) {
                System.err.println("ERROR: Invalid or missing order ID in VNPay response");
                response.put("status", "error");
                response.put("message", "Invalid order ID in payment response");
                return ResponseEntity.badRequest().body(response);
            }

            // Get response code and transaction number
            String responseCode = params.get("vnp_ResponseCode");
            String transactionNo = params.get("vnp_TransactionNo");

            System.out.println("Processing payment return - Order ID: " + orderId + ", Response Code: " + responseCode);

            // Handle potential transaction lookup
            try {
                Transaction transaction = transactionRepository.findById(orderId).orElse(null);

                if (transaction != null) {
                    // Update transaction details
                    transaction.setTransactionId(transactionNo);

                    // Set status based on VNPay response code (00 = success)
                    if ("00".equals(responseCode)) {
                        transaction.setStatus("SUCCESS");
                        response.put("status", "success");
                        response.put("message", "Payment successful");
                    } else {
                        transaction.setStatus("FAILED");
                        transaction.setResultCode(responseCode);
                        response.put("status", "failed");
                        response.put("message", "Payment failed with code: " + responseCode);
                    }

                    transactionRepository.save(transaction);
                    return ResponseEntity.ok(response);
                } else {
                    System.err.println("Transaction not found in database: " + orderId);
                    response.put("status", "error");
                    response.put("message", "Transaction not found: " + orderId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            } catch (Exception dbEx) {
                System.err.println("Database error: " + dbEx.getMessage());
                response.put("status", "error");
                response.put("message", "Database error: " + dbEx.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "An error occurred processing the payment response: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/vnpay/status/{orderId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTransactionStatus(@PathVariable String orderId) {
        // Your existing code...
        // No changes needed here
        Map<String, Object> response = new HashMap<>();

        try {
            Transaction transaction = transactionRepository.findById(orderId).orElse(null);

            if (transaction != null) {
                response.put("status", "success");
                response.put("transactionStatus", transaction.getStatus());
                response.put("orderId", transaction.getOrderId());
                response.put("amount", transaction.getAmount());
                response.put("createdAt", transaction.getCreatedAt());
                response.put("transactionId", transaction.getTransactionId());
                response.put("resultCode", transaction.getResultCode());
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Transaction not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Add this new endpoint for browser redirects from VNPay (note the hyphen)
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