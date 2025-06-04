package com.GHSMSystemBE.GHSMSystem.Dto.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_service_appointment_history")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ServiceHistoryResult {
    @Id
    @Column(name="id", nullable = false, unique = true)
    private String id;
    @Column(name="service_booking_order_id", nullable = false, unique = true)
    private String serviceBookingOrderId;
    @Column(name="content", nullable = false, unique = true)
    private String content;
    @Column(name="is_active", nullable = false, unique = false)
    private boolean isActive;
}
