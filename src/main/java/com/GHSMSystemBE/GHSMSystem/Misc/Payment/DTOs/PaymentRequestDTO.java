package com.GHSMSystemBE.GHSMSystem.Misc.Payment.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payment request for VNPay QR code generation")
public class PaymentRequestDTO {

    @Schema(description = "User ID requesting payment", example = "TranDucHai2123", required = true)
    private String userId;

    @Schema(description = "Payment amount in VND", example = "10000", required = true)
    private String amount;

    @Schema(description = "Description of payment", example = "Payment for healthcare services")
    private String orderInfo;
}