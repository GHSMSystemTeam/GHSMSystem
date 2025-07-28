package com.GHSMSystemBE.GHSMSystem.Misc.Payment;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transaction") // Good - avoids SQL reserved keyword
@Data
public class Transaction {
    @Id
    private String orderId;
    private BigDecimal amount;
    private String orderInfo;
    private String transactionId;
    private String resultCode;
    private String status;
    private String userId;
    private String userName;
    private String appointmentId;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}