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
@Table(name = "tbl_question_and_answer")
@Schema(description = "Entity representing questions from customers and answers from consultants")
public class Question {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    private String id;

    @Schema(description = "ID of the customer who asked the question")
    @Column(name = "customer_id")
    private String customerId;

    @Schema(description = "ID of the consultant who provided the answer")
    @Column(name = "consultant_id")
    private String consultantId;

    @Schema(description = "Title or summary of the question")
    @Column(name = "title")
    private String title;

    @Schema(description = "Detailed content of the question asked by the customer")
    @Column(name = "content")
    private String content;

    private String answerContent;

    @Schema(description = "Date and time when the question was created")
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Schema(description = "Indicates whether the Q&A is publicly visible")
    @Column(name = "is_public")
    private Boolean isPublic;

    @Schema(description = "Indicates whether the Q&A is active in the system")
    @Column(name = "is_active")
    private Boolean isActive;
}