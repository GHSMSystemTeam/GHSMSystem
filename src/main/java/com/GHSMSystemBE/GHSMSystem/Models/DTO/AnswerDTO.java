package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private String id;
    private String consultantId;  // Renamed for clarity from userId
    private String consultantName;  // Added for frontend display convenience
    private String title;
    private String content;  // Renamed from answerContent for consistency
    private LocalDateTime createDate;
    private Integer rating;
    private Boolean isPublic;
}
