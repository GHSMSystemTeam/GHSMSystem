package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Response DTO for video call operations
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoCallResponse {

    private Long callId;
    private String channelName;
    private String appId;
    private String status;
    private String consultantId;
    private String customerId;
    private String message;
}
