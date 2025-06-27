package com.GHSMSystemBE.GHSMSystem.Models.HealthContent;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@ToString
@Table(name = "tbl_feedback")
@Schema(description = "Entity representing customer feedback for a service appointment")
public class Feedback {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Schema(description = "ID of the customer who provided the feedback")
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User customerId;

    @Schema(description = "ID of the service appointment order that this feedback relates to")
    @ManyToOne
    @JoinColumn(name = "service_booking_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ServiceBooking serviceBookingId;

    @Schema(description = "Title or summary of the feedback")
    @Column(name = "title")
    private String title;

    @Schema(description = "Detailed content of the feedback provided by the customer")
    @Column(name = "content")
    private String content;

    @Schema(description = "Date and time when the feedback was created")
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Schema(description = "Indicates whether the feedback is publicly visible")
    @Column(name = "is_public")
    private Boolean isPublic;

    @Schema(description = "Indicates whether the feedback is active in the system")
    @Column(name = "is_active")
    private Boolean isActive=true;
}