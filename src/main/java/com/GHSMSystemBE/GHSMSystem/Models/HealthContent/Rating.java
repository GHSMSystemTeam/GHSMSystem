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
@Table(name = "tbl_rating")
@Schema(description = "Entity representing ratings given by customers for consultants or services")
public class Rating {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Schema(description = "ID of the customer who provided the rating")
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User customerId;


    @Schema(description = "ID of the consultant being rated")
    @ManyToOne
    @JoinColumn(name = "consultant_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User consultantId;

    @Schema(description = "ID of the service being rated")
    @OneToOne
    @JoinColumn(name = "service_booking_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ServiceBooking serviceBookingId;

    @Schema(description = "Title or summary of the rating")
    @Column(name = "title")
    private String title;

    @Schema(description = "Numerical rating value provided by the customer")
    @Column(name = "rating")
    private Float rating;

    @Schema(description = "Detailed content or review accompanying the rating")
    @Column(name = "content")
    private String content;

    @Schema(description = "Date and time when the rating was created")
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Schema(description = "Indicates whether the rating is publicly visible")
    @Column(name = "is_public")
    private Boolean isPublic;

    @Schema(description = "Indicates whether the rating is active in the system")
    @Column(name = "is_active")
    private Boolean isActive;
}