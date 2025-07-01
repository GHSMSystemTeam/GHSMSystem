package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthService;

import com.GHSMSystemBE.GHSMSystem.Configs.AgoraConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AgoraTokenService {
    @Autowired
    private AgoraConfig agoraConfig;

    // Get Agora App ID for client-side SDK initialization
    public String getAppId() {
        return agoraConfig.getAppId();
    }

    // can not be done when don't have agora sdk
    /**
     * Generate RTC token for video call
     * @param channelName The channel name for the call
     * @param uid User ID (can be 0 for auto-assignment)
     * @param role User role (PUBLISHER for video call participants)
     * @return Generated RTC token
     */
    /*public String generateRtcToken(String channelName, int uid, Role role) {
        if (agoraConfig.getAppId() == null || agoraConfig.getAppCertificate() == null) {
            throw new RuntimeException("Agora App ID and App Certificate must be configured");
        }

        int privilegeExpiredTs = (int) (System.currentTimeMillis() / 1000 + agoraConfig.getTokenExpiration());

        return RtcTokenBuilder.buildTokenWithUid(
                agoraConfig.getAppId(),
                agoraConfig.getAppCertificate(),
                channelName,
                uid,
                role,
                privilegeExpiredTs
        );
    }*/

    /**
     * Generate RTC token with string UID
     */
    /*public String generateRtcTokenWithAccount(String channelName, String account, Role role) {
        if (agoraConfig.getAppId() == null || agoraConfig.getAppCertificate() == null) {
            throw new RuntimeException("Agora App ID and App Certificate must be configured");
        }

        int privilegeExpiredTs = (int) (System.currentTimeMillis() / 1000 + agoraConfig.getTokenExpiration());

        return RtcTokenBuilder.buildTokenWithAccount(
                agoraConfig.getAppId(),
                agoraConfig.getAppCertificate(),
                channelName,
                account,
                role,
                privilegeExpiredTs
        );
    }*/
}
