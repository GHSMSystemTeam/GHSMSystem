package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthContent;

import com.GHSMSystemBE.GHSMSystem.Models.ModelSpecification.AnswerSpecifications;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Answer;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthContentRepo.HealthAnswerRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthAnswers;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnswerService implements IHealthAnswers {
    @Autowired
    private HealthAnswerRepo harepo;

    @Autowired
    private IUserService userService;

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
        if(newAnswer == null) {
            return null;
        }
        return harepo.save(newAnswer);
    }

    @Override
    public Answer editAnswer(Answer updatedAnswer) {
        if(updatedAnswer == null) {
            return null;
        }
        return harepo.findById(updatedAnswer.getId())
                .map(old -> {
                    old.setTitle(updatedAnswer.getTitle());
                    old.setAnswerContent(updatedAnswer.getAnswerContent()+"(edited)");
                    old.setUser(updatedAnswer.getUser());
                    old.setIsActive(updatedAnswer.getIsActive());
                    old.setIsPublic(updatedAnswer.getIsPublic());
                    old.setRating(updatedAnswer.getRating());
                    return harepo.save(old);
                })
                .orElse(null);
    }

    @Override
    public void deleteAnswer(Answer answer) {
        harepo.delete(answer);
    }

    @Override
    public Answer getById(String answerId) {
        if(answerId == null) {
            return null;
        }
        UUID uuid = UUID.fromString(answerId);
        return harepo.findById(uuid).orElse(null);
    }

    @Override
    public void active(Answer answer) {
        answer.setIsActive(true);
        harepo.save(answer);
    }

    @Override
    public void deActive(Answer answer) {
        answer.setIsActive(false);
        harepo.save(answer);
    }

    @Override
    public List<Answer> getByUser(String id) {
       User user = userService.getById(id);
       if (user ==null)
       {
           return  null;
       }
       else
       {
           List<Answer> list = harepo.findAll(AnswerSpecifications.madeByUser(user));
           return list;
       }
    }
}