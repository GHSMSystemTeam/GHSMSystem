package com.GHSMSystemBE.GHSMSystem.Misc.Payment.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payment response")
public class PaymentResponseDTO {
    @Schema(description = "Payment Status code")
    private String status;
    @Schema(description = "Error message (if any)")
    private String message;
}
