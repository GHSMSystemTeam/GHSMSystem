package com.GHSMSystemBE.GHSMSystem.Dto.healthContent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "tbl_system_notification")
public class SystemNotification {
    @Id
    private String id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "service_appointment_order_id")
    private String serviceAppointmentOrderId;

    @Column(name = "type")
    private String type;

    @Column(name = "content")
    private String content;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}