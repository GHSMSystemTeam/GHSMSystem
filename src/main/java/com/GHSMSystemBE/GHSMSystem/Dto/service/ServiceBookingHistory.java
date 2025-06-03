package com.GHSMSystemBE.GHSMSystem.Dto.service;

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
public class ServiceBookingHistory {
    @Id
    private String id;
    private String  consultantId;
    private String customerId;
    private String serviceTypeId;
    private String serviceBookingOrderId;
    private boolean isActive;
}
