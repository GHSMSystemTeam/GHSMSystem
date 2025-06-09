package com.GHSMSystemBE.GHSMSystem.Models.HealthContent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "tbl_post")
@Schema(description = "Entity representing a health-related post created by consultants")
public class Post {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Schema(description = "ID of the consultant who authored this post. Must not be empty")
    @Column(name = "consultant_id", nullable = false, unique = false)
    private String consultantId;

    @Schema(description = "ID of the category this post belongs to. Must not be empty")
    @Column(name = "category_id", nullable = false, unique = false)
    private String categoryId;

    @Schema(description = "Title of the post. Must not be empty- must be unique")
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Schema(description = "Main content of the post. Must not be empty- must be unique")
    @Column(name = "content", nullable = false, unique = true)
    private String content;

    @Schema(description = "Average rating score given to this post by users")
    @Column(name = "ratings", nullable = true, unique = false)
    private float ratings;

    @Schema(description = "Collected feedback on this post")
    @Column(name = "feedback", nullable = true, unique = false)
    private String feedback;
}