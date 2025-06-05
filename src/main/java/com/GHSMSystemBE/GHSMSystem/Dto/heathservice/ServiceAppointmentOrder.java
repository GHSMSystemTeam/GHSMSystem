package com.GHSMSystemBE.GHSMSystem.Dto.heathservice;

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
public class ServiceAppointmentOrder {
    @Id
    @Column(name="id", nullable = false, unique = true)
    private String id;
    @Column(name="consultant_id", nullable = false, unique = false)
    private String consultantId;
    @Column(name="customer_id", nullable = false, unique = false)
    private String customerId;
    @Column(name="service_type_id", nullable = false, unique = false)
    private String serviceTypeId;
    @Column(name="create_date", nullable = false, unique = false)
    private LocalDateTime createDate;
    @Column(name="appointment_date", nullable = false, unique = false)
    private Date appointmentDate;
    @Column(name="appointment_slot", nullable = false, unique = false)
    private String appointmentSlot; //AMS-x, PMS-x syntaxId
    @Column(name="duration", nullable = false, unique = false)
    private int  duration; //hours
    @Column(name="description", nullable = false, unique = false)
    private String description;
    @Column(name="is_active", nullable = false, unique = false)
    private boolean isActive;
}
