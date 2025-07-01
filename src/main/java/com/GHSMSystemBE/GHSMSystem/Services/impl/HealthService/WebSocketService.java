package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthService;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.CallNotificationMessage;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Notify user about incoming call
     */
    public void notifyIncomingCall(User customerId, Long callId, String channelName, User consultantId) {
        CallNotificationMessage message = CallNotificationMessage.builder()
                .type("INCOMING_CALL")
                .callId(callId)
                .fromUserId(consultantId.getId().toString())
                .toUserId(customerId.getId().toString())
                .channelName(channelName)
                .message("Incoming video call")
                .timestamp(System.currentTimeMillis())
                .build();

        messagingTemplate.convertAndSendToUser(
                customerId.getId().toString(),
                "/queue/call-notifications",
                message
        );
    }

    /**
     * Notify caller that call was accepted
     */
    public void notifyCallAccepted(User consultantId, Long callId) {
        CallNotificationMessage message = CallNotificationMessage.builder()
                .type("CALL_ACCEPTED")
                .callId(callId)
                .toUserId(consultantId.getId().toString())
                .message("Call was accepted")
                .timestamp(System.currentTimeMillis())
                .build();

        messagingTemplate.convertAndSendToUser(
                consultantId.getId().toString(),
                "/queue/call-notifications",
                message
        );
    }

    /**
     * Notify caller that call was declined
     */
    public void notifyCallDeclined(User consultantId, Long callId) {
        CallNotificationMessage message = CallNotificationMessage.builder()
                .type("CALL_DECLINED")
                .callId(callId)
                .toUserId(consultantId.getId().toString())
                .message("Call was declined")
                .timestamp(System.currentTimeMillis())
                .build();

        messagingTemplate.convertAndSendToUser(
                consultantId.getId().toString(),
                "/queue/call-notifications",
                message
        );
    }

    /**
     * Notify user that call ended
     */
    public void notifyCallEnded(User userId, Long callId) {
        CallNotificationMessage message = CallNotificationMessage.builder()
                .type("CALL_ENDED")
                .callId(callId)
                .toUserId(userId.getId().toString())
                .message("Call ended")
                .timestamp(System.currentTimeMillis())
                .build();

        messagingTemplate.convertAndSendToUser(
                userId.getId().toString(),
                "/queue/call-notifications",
                message
        );
    }
}
