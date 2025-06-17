package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthContent;

import com.GHSMSystemBE.GHSMSystem.Models.AnswerSpecifications;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Answer;
import com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo.userRepo;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthContentRepo.HealthAnswerRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthAnswers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class AnswerService implements IHealthAnswers {
    @Autowired
    private HealthAnswerRepo harepo;
    @Autowired
    private userRepo urepo;
    @Override
    public List<Answer> getAll() {
        return harepo.findAll();
    }

    @Override
    public List<Answer> getAllActiveAnswers() {
       return harepo.findAll(AnswerSpecifications.hasStatusTrue());
    }

    @Override
    public List<Answer> getAllInactiveAnswers() {
        return harepo.findAll(AnswerSpecifications.hasStatusFalse());
    }

    @Override
    public Answer createAnswer(Answer newAnswer) {
        return null;
    }

    @Override
    public Answer editAnswer(Answer updatedAnswer) {
        return null;
    }

    @Override
    public void deleteAnswer(Answer answer) {

    }

    @Override
    public Answer getById(UUID answerId) {
        return null;
    }

    @Override
    public void active(Answer answer) {

    }

    @Override
    public void deActive(Answer answer) {

    }
}
