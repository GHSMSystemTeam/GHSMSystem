package com.GHSMSystemBE.GHSMSystem.Misc.Payment.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Transaction status response")
public class TransactionResponseDTO {

    @Schema(description = "Response status", example = "success")
    private String status;
    @Schema(description = "Transaction status", example = "SUCCESS")
    private String transactionStatus;
    @Schema(description = "Order ID", example = "GHSM_1688795432123")
    private String orderId;
    @Schema(description = "Transaction amount", example = "100000")
    private BigDecimal amount;
    @Schema(description = "Transaction ID", example = "13554592")
    private String transactionId;
    @Schema(description = "Result code", example = "00")
    private String resultCode;
    @Schema(description = "Error message", example = "Transaction not found")
    private String message;
    @Schema(description = "Transaction creation time")
    private LocalDateTime createDate;
}
