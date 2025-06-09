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
@Table(name = "tbl_answer")
@Schema(description = "Entity representing answers from consultants to customer questions")
public class Answer {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    private String id;

    @Schema(description = "ID of the user who provided the answer")
    @Column(name = "user_id")
    private String userId;

    @Schema(description = "Title or summary of the answer")
    @Column(name = "title")
    private String title;

    @Schema(description = "Detailed content of the question being answered")
    @Column(name = "content")
    private String content;

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
    private Boolean isActive;

    // Added as per the template provided
    public static final String CREATED_BY = "TranDucHai2123";
    public static final LocalDateTime CREATION_TIMESTAMP = LocalDateTime.parse("2025-06-08T14:45:10");
}