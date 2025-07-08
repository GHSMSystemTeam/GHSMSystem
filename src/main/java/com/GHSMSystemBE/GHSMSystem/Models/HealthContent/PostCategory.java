package com.GHSMSystemBE.GHSMSystem.Models.HealthContent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Schema(description = "Name of the post category")
    @Column(name = "name")
    private String name;

    @Schema(description = "Indicates whether this category is currently active")
    @Column(name = "is_active")
    private Boolean isActive;
}