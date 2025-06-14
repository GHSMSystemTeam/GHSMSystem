package com.GHSMSystemBE.GHSMSystem.Models.HealthContent;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "tbl_system_notification")
@Schema(description = "Entity representing system notifications sent to users")
public class SystemNotification {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Schema(description = "ID of the customer who will receive this notification")
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false,unique = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User customerId;

    @Schema(description = "ID of the related service appointment order (if applicable)")
    @OneToOne
    @JoinColumn(name="service_booking_id", nullable = false, unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ServiceBooking serviceBookingId;

    @Schema(description = "Type or category of the notification")
    @Column(name = "type")
    private String type;

    @Schema(description = "The actual message content of the notification")
    @Column(name = "content")
    private String content;

    @Schema(description = "Indicates whether the notification is active in the system")
    @Column(name = "is_active")
    private Boolean isActive;

    @Schema(description = "Date and time when the notification was created")
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}