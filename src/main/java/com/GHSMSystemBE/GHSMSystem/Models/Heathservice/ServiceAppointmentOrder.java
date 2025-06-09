package com.GHSMSystemBE.GHSMSystem.Models.Heathservice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_service_appointment_order")
@Data
@ToString
@Schema(description = "Entity representing a service appointment order in the system")
public class ServiceAppointmentOrder {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @Column(name="id", nullable = false, unique = true)
    private String id;

    @Schema(description = "ID of the consultant assigned to this appointment. Must not be empty")
    @Column(name="consultant_id", nullable = false, unique = false)
    private String consultantId;

    @Schema(description = "ID of the customer who requested this appointment. Must not be empty")
    @Column(name="customer_id", nullable = false, unique = false)
    private String customerId;

    @Schema(description = "ID of the service type for this appointment. Must not be empty")
    @Column(name="service_type_id", nullable = false, unique = false)
    private String serviceTypeId;

    @Schema(description = "Date and time when the appointment was created. Must not be empty")
    @Column(name="create_date", nullable = false, unique = false)
    private LocalDateTime createDate;

    @Schema(description = "Date scheduled for the appointment. Must not be empty")
    @Column(name="appointment_date", nullable = false, unique = false)
    private Date appointmentDate;

    @Schema(description = "Time slot for the appointment (AMS-x, PMS-x syntax). Must not be empty")
    @Column(name="appointment_slot", nullable = false, unique = false)
    private String appointmentSlot; //AMS-x, PMS-x syntaxId

    @Schema(description = "Duration of the appointment in hours. Must not be empty")
    @Column(name="duration", nullable = false, unique = false)
    private int duration; //hours

    @Schema(description = "Description of the appointment and related information. Must not be empty")
    @Column(name="description", nullable = false, unique = false)
    private String description;

    @Schema(description = "Indicates whether the appointment is active. Must not be empty")
    @Column(name="is_active", nullable = false, unique = false)
    private boolean isActive;
}