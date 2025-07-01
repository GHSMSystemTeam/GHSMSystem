package com.GHSMSystemBE.GHSMSystem.Models.HealthService;

import com.GHSMSystemBE.GHSMSystem.Models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "video_calls")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "channel_name", nullable = false, unique = true)
    private String channelName;

    @Schema(description = "ID of the consultant assigned to this appointment. Must not be empty")
    @ManyToOne
    @JoinColumn(name = "consultant_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User consultantId;

    @Schema(description = "ID of the customer who requested this appointment. Must not be empty")
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User customerId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CallStatus status;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = CallStatus.INITIATED;
        }
    }

    public enum CallStatus {
        INITIATED,
        RINGING,
        ACCEPTED,
        DECLINED,
        ENDED,
        MISSED,
        CANCELLED
    }
}
