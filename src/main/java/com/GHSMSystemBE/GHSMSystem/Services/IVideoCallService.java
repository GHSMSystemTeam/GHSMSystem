package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.VideoCallRequest;
import com.GHSMSystemBE.GHSMSystem.Models.DTO.VideoCallResponse;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.VideoCall;

import java.util.List;

public interface IVideoCallService {
    VideoCallResponse initiateCall(VideoCallRequest request);
    VideoCallResponse acceptCall(String callId, String userId);
    void declineCall(String callId, String userId);
    void endCall(String callId, String userId);
    VideoCall getCallDetails(String callId);
    String generateChannelName(String consultantId, String customerId);
    List<VideoCall> getAll();
}
