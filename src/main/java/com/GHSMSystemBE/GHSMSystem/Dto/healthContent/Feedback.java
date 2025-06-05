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
@Table(name = "tbl_feedback")
public class Feedback {
    @Id
    private String id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "service_appointment_order_id")
    private String serviceAppointmentOrderId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "is_active")
    private Boolean isActive;
}