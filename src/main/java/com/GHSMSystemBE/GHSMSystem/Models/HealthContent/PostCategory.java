package com.GHSMSystemBE.GHSMSystem.Models.HealthContent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "tbl_post_category")
@Schema(description = "Entity representing a category for health-related posts")
public class PostCategory {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    private String id;

    @Schema(description = "Name of the post category")
    @Column(name = "name")
    private String name;

    @Schema(description = "Indicates whether this category is currently active")
    @Column(name = "is_active")
    private Boolean isActive;
}