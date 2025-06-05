package com.GHSMSystemBE.GHSMSystem.Models.healthContent;

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
public class Rating {
    @Id
    private String id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "consultant_id")
    private String consultantId;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "is_active")
    private Boolean isActive;
}