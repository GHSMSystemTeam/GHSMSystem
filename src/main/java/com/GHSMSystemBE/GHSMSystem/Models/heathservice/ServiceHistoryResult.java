package com.GHSMSystemBE.GHSMSystem.Models.heathservice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_service_appointment_history_result")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(description = "Entity representing the historical record of a service appointment")
public class ServiceHistoryResult {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @Column(name="id", nullable = false, unique = true)
    private String id;

    @Schema(description = "Reference to the original service booking order. Must not be empty- must be unique")
    @Column(name="service_booking_order_id", nullable = false, unique = true)
    private String serviceBookingOrderId;

    @Schema(description = "Content or results of the service appointment. Must not be empty- must be unique")
    @Column(name="content", nullable = false, unique = true)
    private String content;

    @Schema(description = "Indicates whether this history record is active. Must not be empty")
    @Column(name="is_active", nullable = false, unique = false)
    private boolean isActive;
}