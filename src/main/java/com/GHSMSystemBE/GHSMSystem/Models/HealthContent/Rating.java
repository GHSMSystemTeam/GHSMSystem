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
@Table(name = "tbl_rating")
@Schema(description = "Entity representing ratings given by customers for consultants or services")
public class Rating {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    private String id;

    @Schema(description = "ID of the customer who provided the rating")
    @Column(name = "customer_id")
    private String customerId;

    @Schema(description = "ID of the consultant being rated")
    @Column(name = "consultant_id")
    private String consultantId;

    @Schema(description = "ID of the service being rated")
    @Column(name = "service_id")
    private String serviceId;

    @Schema(description = "Title or summary of the rating")
    @Column(name = "title")
    private String title;

    @Schema(description = "Numerical rating value provided by the customer")
    @Column(name = "rating")
    private Float rating;

    @Schema(description = "Detailed content or review accompanying the rating")
    @Column(name = "content")
    private String content;

    @Schema(description = "Date and time when the rating was created")
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Schema(description = "Indicates whether the rating is publicly visible")
    @Column(name = "is_public")
    private Boolean isPublic;

    @Schema(description = "Indicates whether the rating is active in the system")
    @Column(name = "is_active")
    private Boolean isActive;
}