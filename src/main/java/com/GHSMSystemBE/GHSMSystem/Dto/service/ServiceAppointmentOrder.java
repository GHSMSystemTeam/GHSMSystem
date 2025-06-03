package com.GHSMSystemBE.GHSMSystem.Dto.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_service_appointment_order")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ServiceAppointmentOrder {
    @Id
    @Column(name="id", nullable = false, unique = true)
    private String id;
    private String consultantId;
    private String customerId;
    private String serviceTypeId;
    private LocalDateTime createDate;
    private Date appointmentDate;
    private String appointmentSlot; //AMS-x, PMS-x syntaxId
    private int  duration; //hours
    private String description;
    private boolean isActive;
}
