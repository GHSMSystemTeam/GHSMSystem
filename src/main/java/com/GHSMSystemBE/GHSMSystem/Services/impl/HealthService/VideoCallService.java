package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthService;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.VideoCallRequest;
import com.GHSMSystemBE.GHSMSystem.Models.DTO.VideoCallResponse;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.VideoCall;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo.VideoCallRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import com.GHSMSystemBE.GHSMSystem.Services.IVideoCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class VideoCallService implements IVideoCallService {

    @Autowired
    private VideoCallRepo videoCallRepository;

    @Autowired
    private AgoraTokenService agoraTokenService;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private IUserService userService;

    /**
     * Initiate a video call between two users
     */
    @Override
    public VideoCallResponse initiateCall(VideoCallRequest request) {
        // Check if there's already an active call between these users
        User con = userService.getById(request.getConsultantId());
        User cus = userService.getById(request.getCustomerId());

        // Generate unique channel name
        String channelName = generateChannelName(con.getId().toString(), cus.getId().toString());

        // Create video call record
        VideoCall videoCall = new VideoCall();
        videoCall.setChannelName(channelName);
        videoCall.setConsultantId(con);
        videoCall.setCustomerId(cus);
        videoCall.setStatus(VideoCall.CallStatus.INITIATED);
        videoCall.setCreatedAt(LocalDateTime.now());

        videoCall = videoCallRepository.save(videoCall);

        // Notify receiver about incoming call via WebSocket
        webSocketService.notifyIncomingCall(cus, videoCall.getId(),
                channelName, con);

        return VideoCallResponse.builder()
                .callId(videoCall.getId())
                .channelName(channelName)
                .appId(agoraTokenService.getAppId())
                .status(videoCall.getStatus().toString())
                .build();
    }

    /**
     * Accept an incoming video call
     */
    @Override
    public VideoCallResponse acceptCall(String callId, String userId) {
        User cus = userService.getById(userId);

        Long callIdLong = Long.parseLong(callId);

        VideoCall videoCall = videoCallRepository.findById(callIdLong)
                .orElseThrow(() -> new RuntimeException("Video call not found"));

        /*if (!videoCall.getCustomerId().getId().toString().equals(userId)) {
            throw new RuntimeException("Only the receiver can accept the call");
        }*/

        if (videoCall.getStatus() != VideoCall.CallStatus.INITIATED ||
                videoCall.getStatus() != VideoCall.CallStatus.RINGING) {
            throw new RuntimeException("Call cannot be accepted in current status: " + videoCall.getStatus());
        }

        videoCall.setStatus(VideoCall.CallStatus.ACCEPTED);
        videoCall.setStartedAt(LocalDateTime.now());
        videoCall = videoCallRepository.save(videoCall);

        // Notify caller that call was accepted
        webSocketService.notifyCallAccepted(videoCall.getConsultantId(), callIdLong);

        return VideoCallResponse.builder()
                .callId(videoCall.getId())
                .channelName(videoCall.getChannelName())
                .appId(agoraTokenService.getAppId())
                .status(videoCall.getStatus().toString())
                .build();
    }

    /**
     * Decline an incoming video call
     */
    @Override
    public void declineCall(String callId, String userId) {

        Long callIdLong = Long.parseLong(callId);

        VideoCall videoCall = videoCallRepository.findById(callIdLong)
                .orElseThrow(() -> new RuntimeException("Video call not found"));

        if (!videoCall.getCustomerId().getId().toString().equals(userId)) {
            throw new RuntimeException("Only the receiver can decline the call");
        }

        videoCall.setStatus(VideoCall.CallStatus.DECLINED);
        videoCall.setEndedAt(LocalDateTime.now());
        videoCallRepository.save(videoCall);

        // Notify caller that call was declined
        webSocketService.notifyCallDeclined(videoCall.getConsultantId(), callIdLong);
    }

    /**
     * End an active video call
     */
    @Override
    public void endCall(String callId, String userId) {
        User user = userService.getById(userId);

        Long callIdLong = Long.parseLong(callId);

        VideoCall videoCall = videoCallRepository.findById(callIdLong)
                .orElseThrow(() -> new RuntimeException("Video call not found"));

        /*if (!videoCall.getConsultantId().getId().toString().equals(userId) && !videoCall.getCustomerId().toString().equals(userId)) {
            throw new RuntimeException("Only call participants can end the call");
        }*/

        videoCall.setStatus(VideoCall.CallStatus.ENDED);
        videoCall.setEndedAt(LocalDateTime.now());

        if (videoCall.getStartedAt() != null) {
            long duration = ChronoUnit.SECONDS.between(videoCall.getStartedAt(), videoCall.getEndedAt());
            videoCall.setDurationSeconds((int) duration);
        }

        videoCallRepository.save(videoCall);

        // Notify other participant that call ended
        User otherUserId = videoCall.getConsultantId().getId().toString().equals(userId) ?
                videoCall.getCustomerId() : videoCall.getConsultantId();
        webSocketService.notifyCallEnded(otherUserId, callIdLong);
    }

    /**
     * Get call details
     */
    @Override
    public VideoCall getCallDetails(String callId) {

        Long callIdLong = Long.parseLong(callId);

        return videoCallRepository.findById(callIdLong)
                .orElseThrow(() -> new RuntimeException("Video call not found"));
    }

    @Override
    public String generateChannelName(String consultantId, String customerId) {
        return "call_" + consultantId + "_" + customerId +
                "_" + System.currentTimeMillis();
    }

    @Override
    public List<VideoCall> getAll() {
        return videoCallRepository.findAll();
    }
}
