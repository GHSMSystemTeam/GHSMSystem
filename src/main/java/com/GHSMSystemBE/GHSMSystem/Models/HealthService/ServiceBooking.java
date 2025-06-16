package com.GHSMSystemBE.GHSMSystem.Models.HealthService;

import com.GHSMSystemBE.GHSMSystem.Models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tbl_service_booking")
@Data
@ToString
@Schema(description = "Entity representing a service appointment order in the system")
public class ServiceBooking {                                            
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id", nullable = false, unique = true)
    private UUID id;

    @Schema(description = "ID of the consultant assigned to this appointment. Must not be empty")
    @ManyToOne
    @JoinColumn(name="consultant_id", nullable = true, unique = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User consultantId;

    @Schema(description = "ID of the customer who requested this appointment. Must not be empty")
    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false, unique = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User customerId;

    @Schema(description = "ID of the service type for this appointment. Must not be empty")
    @ManyToOne
    @JoinColumn(name="service_type_id", nullable = false, unique = false)
    private ServiceType serviceTypeId;

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

    public UUID getId() {
        return id;
    }

    public User getConsultantId() {
        return consultantId;
    }

    public User getCustomerId() {
        return customerId;
    }

    public ServiceType getServiceTypeId() {
        return serviceTypeId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentSlot() {
        return appointmentSlot;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setConsultantId(User consultantId) {
        this.consultantId = consultantId;
    }

    public void setCustomerId(User customerId) {
        this.customerId = customerId;
    }

    public void setServiceTypeId(ServiceType serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAppointmentSlot(String appointmentSlot) {
        this.appointmentSlot = appointmentSlot;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}