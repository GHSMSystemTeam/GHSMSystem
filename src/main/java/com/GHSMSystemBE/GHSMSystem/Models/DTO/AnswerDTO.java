package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private String questionId;
    private String consultantId;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private Integer rating;
    private Boolean isPublic;
}
