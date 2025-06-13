package com.GHSMSystemBE.GHSMSystem.Models.HealthContent;

import com.GHSMSystemBE.GHSMSystem.Models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Entity
@Data
@ToString
@Table(name = "tbl_post")
@Schema(description = "Entity representing a health-related post created by consultants")
public class Post {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Schema(description = "ID of the consultant who authored this post. Must not be empty")
    @ManyToOne
    @JoinColumn(name = "consultant_id", nullable = false, unique = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User consultantId;

    @Schema(description = "ID of the category this post belongs to. Must not be empty")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, unique = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PostCategory categoryId;

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