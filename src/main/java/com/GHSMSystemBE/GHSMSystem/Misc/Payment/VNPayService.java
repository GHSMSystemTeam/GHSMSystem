package com.GHSMSystemBE.GHSMSystem.Misc.Payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService {
    @Value("${vnpay.terminal-id}")
    private String vnpTerminalId;

    @Value("${vnpay.secret-key}")
    private String vnpSecretKey;

    @Value("${vnpay.payment-url}")
    private String vnpPayUrl;

    @Value("${vnpay.return-url}")
    private String vnpReturnUrl;

    public String createPaymentUrl(String orderId, String amount, String orderInfo, String ipAddress) {
        try {
            Map<String, String> vnpParams = new HashMap<>();
            vnpParams.put("vnp_Version", "2.1.0");
            vnpParams.put("vnp_Command", "pay");
            vnpParams.put("vnp_TmnCode", vnpTerminalId);

            // Check if amount is null or empty
            if (amount == null || amount.trim().isEmpty()) {
                throw new IllegalArgumentException("Payment amount cannot be null or empty");
            }

            // Parse the amount as BigDecimal to handle decimals
            java.math.BigDecimal amountBd = new java.math.BigDecimal(amount);
            // Multiply by 100 and convert to long
            long amountInVnd = amountBd.multiply(new java.math.BigDecimal("100")).longValue();
            vnpParams.put("vnp_Amount", String.valueOf(amountInVnd));


            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
            String createDate = formatter.format(calendar.getTime());

            vnpParams.put("vnp_CreateDate", createDate);
            vnpParams.put("vnp_CurrCode", "VND");
            vnpParams.put("vnp_IpAddr", ipAddress != null ? ipAddress : "127.0.0.1");
            vnpParams.put("vnp_Locale", "vn");
            vnpParams.put("vnp_OrderInfo", orderInfo != null ? orderInfo : "Payment for services");
            vnpParams.put("vnp_OrderType", "other");
            vnpParams.put("vnp_ReturnUrl", vnpReturnUrl);
            vnpParams.put("vnp_TxnRef", orderId);

            // Sort parameters and build query string
            List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
            Collections.sort(fieldNames);

            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();

            Iterator<String> itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = vnpParams.get(fieldName);
                if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                    // Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                    // Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }

            String queryUrl = query.toString();
            String vnpSecureHash = hmacSHA512(vnpSecretKey, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

            return vnpPayUrl + "?" + queryUrl;
        } catch (Exception e) {
            throw new RuntimeException("Error creating VNPay URL: " + e.getMessage(), e);
        }
    }

  
    private String hmacSHA512(String key, String data) {
        try {
            Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            sha512_HMAC.init(secret_key);
            byte[] hash = sha512_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC-SHA512", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}