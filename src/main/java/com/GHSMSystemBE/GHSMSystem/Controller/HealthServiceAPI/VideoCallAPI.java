package com.GHSMSystemBE.GHSMSystem.Controller.HealthServiceAPI;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.VideoCallRequest;
import com.GHSMSystemBE.GHSMSystem.Models.DTO.VideoCallResponse;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.VideoCall;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Services.IVideoCallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/video-calls")
@Tag(name = "Video Call", description = "Video calling functionality using Agora")
@CrossOrigin(origins = "*")
public class VideoCallAPI {

    @Autowired
    private IVideoCallService videoCallService;

    @PostMapping("/initiate")
    @Operation(summary = "Initiate a video call", description = "Start a one-to-one video call between consultant and customer")
    public ResponseEntity<VideoCallResponse> initiateCall(
            @Valid @RequestBody VideoCallRequest request) {
        try {
            VideoCallResponse response = videoCallService.initiateCall(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{callId}/accept")
    @Operation(summary = "Accept a video call", description = "Accept an incoming video call")
    public ResponseEntity<VideoCallResponse> acceptCall(
            @Parameter(description = "Call ID") @PathVariable String callId,
            @Parameter(description = "Customer ID accepting the call") @RequestParam String userId) {
        try {
            VideoCallResponse response = videoCallService.acceptCall(callId, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{callId}/decline")
    @Operation(summary = "Decline a video call", description = "Decline an incoming video call")
    public ResponseEntity<Void> declineCall(
            @Parameter(description = "Call ID") @PathVariable String callId,
            @Parameter(description = "Customer ID declining the call") @RequestParam String userId) {
        try {
            videoCallService.declineCall(callId, userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{callId}/end")
    @Operation(summary = "End a video call", description = "End an active video call")
    public ResponseEntity<Void> endCall(
            @Parameter(description = "Call ID") @PathVariable String callId,
            @Parameter(description = "Customer ID ending the call") @RequestParam String userId) {
        try {
            videoCallService.endCall(callId, userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{callId}")
    @Operation(summary = "Get call details", description = "Retrieve details of a specific video call")
    public ResponseEntity<VideoCall> getCallDetails(
            @Parameter(description = "Call ID") @PathVariable String callId) {
        try {
            VideoCall videoCall = videoCallService.getCallDetails(callId);
            return ResponseEntity.ok(videoCall);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Print user list
    @Operation(summary = "Get a list of all call record", description = "Retrieve a complete list of available video call from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of video retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    @GetMapping("/videcalls")
    public ResponseEntity<List<VideoCall>> getCalls() {
        List<VideoCall> callList = videoCallService.getAll();
        return ResponseEntity.ok(callList);
    }

    /*@PostMapping("/{callId}/token/refresh")
    @Operation(summary = "Refresh Agora token", description = "Generate a new token for an ongoing call")
    public ResponseEntity<String> refreshToken(
            @Parameter(description = "Call ID") @PathVariable Long callId,
            @Parameter(description = "User ID requesting token refresh") @RequestParam Long userId) {
        try {
            VideoCall videoCall = videoCallService.getCallDetails(callId);
            if (!videoCall.getCallerId().equals(userId) && !videoCall.getReceiverId().equals(userId)) {
                return ResponseEntity.forbidden().build();
            }

            // Generate new token (you'll need to implement this in the service)
            // String newToken = videoCallService.refreshToken(callId, userId);
            // return ResponseEntity.ok(newToken);
            return ResponseEntity.ok("Token refresh functionality to be implemented");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }*/
}
