package com.GHSMSystemBE.GHSMSystem.Models.healthContent;

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
@Table(name = "tbl_feedback")
@Schema(description = "Entity representing customer feedback for a service appointment")
public class Feedback {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    private String id;

    @Schema(description = "ID of the customer who provided the feedback")
    @Column(name = "customer_id")
    private String customerId;

    @Schema(description = "ID of the service appointment order that this feedback relates to")
    @Column(name = "service_appointment_order_id")
    private String serviceAppointmentOrderId;

    @Schema(description = "Title or summary of the feedback")
    @Column(name = "title")
    private String title;

    @Schema(description = "Detailed content of the feedback provided by the customer")
    @Column(name = "content")
    private String content;

    @Schema(description = "Date and time when the feedback was created")
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Schema(description = "Indicates whether the feedback is publicly visible")
    @Column(name = "is_public")
    private Boolean isPublic;

    @Schema(description = "Indicates whether the feedback is active in the system")
    @Column(name = "is_active")
    private Boolean isActive;
}