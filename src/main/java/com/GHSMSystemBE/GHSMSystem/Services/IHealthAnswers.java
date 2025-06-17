package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Answer;

import java.util.List;
import java.util.UUID;

public interface IHealthAnswers {
    List<Answer> getAll();
    List<Answer> getAllActiveAnswers();
    List<Answer> getAllInactiveAnswers();
    Answer createAnswer(Answer newAnswer);
    Answer editAnswer(Answer updatedAnswer);
    void deleteAnswer(Answer answer);
    Answer getById(UUID answerId);
    void active(Answer answer);
    void deActive(Answer answer);

}
