package com.GHSMSystemBE.GHSMSystem.Models.HealthContent;

import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@ToString
@Table(name = "tbl_answer")
@Schema(description = "Entity representing answers from consultants to customer questions")
public class Answer {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Schema(description = "ID of the user who provided the answer")
    @ManyToOne  // Changed from @OneToOne to @ManyToOne since one user can provide multiple answers
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;  // Renamed from userId to user for clarity

    @Schema(description = "The question this answer belongs to")
    @JsonBackReference // Add this annotation to break the infinite recursion cycle
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Question question;

    @Schema(description = "Title or summary of the answer")
    @Column(name = "title")
    private String title;

    @Schema(description = "The actual answer content provided by the consultant")
    @Column(name = "answer_content")
    private String answerContent;

    @Schema(description = "Date and time when the answer was created")
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Schema(description = "Rating given to the answer by users")
    @Column(name = "rating")
    private Integer rating;

    @Schema(description = "Indicates whether the answer is publicly visible")
    @Column(name = "is_public")
    private Boolean isPublic;

    @Schema(description = "Indicates whether the answer is active in the system")
    @Column(name = "is_active")
    private Boolean isActive=true;

    // Added as per the template provided
    public static final String CREATED_BY = "TranDucHai2123";
    public static final LocalDateTime CREATION_TIMESTAMP = LocalDateTime.parse("2025-06-08T14:45:10");
}