package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Question;

import java.util.List;
import java.util.UUID;

public interface IHealthQuestion {
    List<Question> getAll();
    List<Question> getAllActiveQuestions();
    List<Question> getAllInactiveQuestions();
    Question createQuestion(Question newQuestion);
    Question editQuestion(Question updatedQuestion);
    void deleteQuestion(Question question);
    Question getById(String questionId);
    void active(Question question);
    void deActive(Question question);
}