package com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.VideoCall;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VideoCallRepo extends JpaRepository<VideoCall, Long> {

    /**
     * Find active call between two users
     */
    @Query("SELECT vc FROM VideoCall vc WHERE " +
            "((vc.consultantId = :conId AND vc.customerId = :cusId) OR " +
            "(vc.consultantId = :cusId AND vc.customerId = :conId)) AND " +
            "vc.status IN ('INITIATED', 'RINGING', 'ACCEPTED')")
    Optional<VideoCall> findActiveCallBetweenUsers(@Param("conId") User con,
                                                   @Param("cusId") User cus);

    /**
     * Find all calls for a specific user
     */
    @Query("SELECT vc FROM VideoCall vc WHERE vc.consultantId = :userId OR vc.customerId = :userId " +
            "ORDER BY vc.createdAt DESC")
    List<VideoCall> findCallsByUserId(@Param("userId") User userId);

    /**
     * Find active calls for a user
     */
    @Query("SELECT vc FROM VideoCall vc WHERE " +
            "(vc.consultantId = :userId OR vc.customerId = :userId) AND " +
            "vc.status IN ('INITIATED', 'RINGING', 'ACCEPTED')")
    List<VideoCall> findActiveCallsByUserId(@Param("userId") User userId);

    /**
     * Find calls by status
     */
    List<VideoCall> findByStatus(VideoCall.CallStatus status);

    /**
     * Find calls created within a date range
     */
    @Query("SELECT vc FROM VideoCall vc WHERE vc.createdAt BETWEEN :startDate AND :endDate " +
            "ORDER BY vc.createdAt DESC")
    List<VideoCall> findCallsInDateRange(@Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate);

    /**
     * Find calls by channel name
     */
    Optional<VideoCall> findByChannelName(String channelName);

    /**
     * Count total calls for a user
     */
    @Query("SELECT COUNT(vc) FROM VideoCall vc WHERE vc.consultantId = :userId OR vc.customerId = :userId")
    Long countCallsByUserId(@Param("userId") User userId);

    /**
     * Get call statistics for a user
     */
    @Query("SELECT " +
            "COUNT(vc) as totalCalls, " +
            "COUNT(CASE WHEN vc.status = 'ACCEPTED' THEN 1 END) as acceptedCalls, " +
            "COUNT(CASE WHEN vc.status = 'DECLINED' THEN 1 END) as declinedCalls, " +
            "COUNT(CASE WHEN vc.status = 'MISSED' THEN 1 END) as missedCalls, " +
            "AVG(vc.durationSeconds) as avgDuration " +
            "FROM VideoCall vc WHERE vc.consultantId = :userId OR vc.customerId = :userId")
    Object[] getCallStatsByUserId(@Param("userId") User userId);
}
