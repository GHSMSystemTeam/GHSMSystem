package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Request DTO for initiating a video call
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoCallRequest {

    @NotNull(message = "Caller ID is required")
    private String consultantId;

    @NotNull(message = "Receiver ID is required")
    private String customerId;

    private String callType = "video";
}
