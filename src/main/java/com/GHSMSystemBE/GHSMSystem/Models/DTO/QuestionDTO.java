package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
