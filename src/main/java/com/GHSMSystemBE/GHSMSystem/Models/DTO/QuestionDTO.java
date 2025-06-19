package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private String customerId;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private Boolean isPublic;
}
