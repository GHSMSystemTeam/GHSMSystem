package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// WebSocket message DTOs
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallNotificationMessage {

    private String type; // INCOMING_CALL, CALL_ACCEPTED, CALL_DECLINED, CALL_ENDED
    private Long callId;
    private String fromUserId;
    private String toUserId;
    private String channelName;
    private String appId;
    private String message;
    private Long timestamp;
}
