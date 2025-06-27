package com.GHSMSystemBE.GHSMSystem.Models.HealthContent;

import com.GHSMSystemBE.GHSMSystem.Models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@ToString
@Table(name = "tbl_question")
@Schema(description = "Entity representing questions from customers and answers from consultants")
public class Question {
    @Schema(description = "ID field. Must not be empty- must be unique")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Schema(description = "Customer who asked the question")
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User customer;

    @Schema(description = "Answers provided to this question")
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Answer> answers = new ArrayList<>();

    @Schema(description = "Title or summary of the question")
    @Column(name = "title")
    private String title;

    @Schema(description = "Detailed content of the question asked by the customer")
    @Column(name = "content")
    private String content;

    @Schema(description = "Date and time when the question was created")
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Schema(description = "Indicates whether the Q&A is publicly visible")
    @Column(name = "is_public")
    private Boolean isPublic;

    @Schema(description = "Indicates whether the Q&A is active in the system")
    @Column(name = "is_active")
    private Boolean isActive = true;

    // Helper methods for managing the bidirectional relationship
    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setQuestion(null);
    }
}