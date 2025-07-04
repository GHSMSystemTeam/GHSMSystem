package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.VideoCallRequest;
import com.GHSMSystemBE.GHSMSystem.Models.DTO.VideoCallResponse;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.VideoCall;

import java.util.List;

public interface IVideoCallService {
    VideoCallResponse initiateCall(VideoCallRequest request);
    VideoCallResponse acceptCall(Long callId, String userId);
    void declineCall(Long callId, String userId);
    void endCall(Long callId, String userId);
    VideoCall getCallDetails(Long callId);
    String generateChannelName(String consultantId, String customerId);
    List<VideoCall> getAll();
}
