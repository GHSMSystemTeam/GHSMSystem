package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Answer;

import java.util.List;

public class QuestionDTO {
    private String id;
    private String customerId;
    private String title;
    private String content;
    private List<Answer> answerList;
}
