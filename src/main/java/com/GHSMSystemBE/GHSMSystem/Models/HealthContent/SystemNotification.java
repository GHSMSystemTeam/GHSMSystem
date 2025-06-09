package com.GHSMSystemBE.GHSMSystem.Models.HealthContent;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entity representing system notifications sent to users")
public class SystemNotification {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    private String id;

    @Schema(description = "ID of the customer who will receive this notification")
    @Column(name = "customer_id")
    private String customerId;

    @Schema(description = "ID of the related service appointment order (if applicable)")
    @Column(name = "service_appointment_order_id")
    private String serviceAppointmentOrderId;

    @Schema(description = "Type or category of the notification")
    @Column(name = "type")
    private String type;

    @Schema(description = "The actual message content of the notification")
    @Column(name = "content")
    private String content;

    @Schema(description = "Indicates whether the notification is active in the system")
    @Column(name = "is_active")
    private Boolean isActive;

    @Schema(description = "Date and time when the notification was created")
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}