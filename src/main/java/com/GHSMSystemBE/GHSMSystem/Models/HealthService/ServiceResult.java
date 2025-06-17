package com.GHSMSystemBE.GHSMSystem.Models.HealthService;

import com.GHSMSystemBE.GHSMSystem.Models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tbl_service_result")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(description = "Entity representing the historical record of a service appointment")
public class ServiceResult {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id", nullable = false, unique = true)
    private UUID id;

    @Schema(description = "Reference to the original service booking order. Must not be empty- must be unique")
    @OneToOne
    @JoinColumn(name="service_booking_id", nullable = false, unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ServiceBooking serviceBookingId;

    @Schema(description = "Reference to the original service booking order. Must not be empty- must be unique")
    @OneToOne
    @JoinColumn(name="customer_id", nullable = false, unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User customerID;


    @Schema(description = "Content or results of the service appointment. Must not be empty- must be unique")
    @Column(name="content", nullable = false, unique = true)
    private String content;

    @Schema(description = "Indicates whether this history record is active. Must not be empty")
    @Column(name="is_active", nullable = false, unique = false)
    private boolean isActive;
}