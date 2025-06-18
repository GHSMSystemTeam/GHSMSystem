package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthContent;

import com.GHSMSystemBE.GHSMSystem.Models.AnswerSpecifications;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Answer;
import com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo.userRepo;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthContentRepo.HealthAnswerRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
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
        if(newAnswer == null)
        {
            return null;
        }
       Boolean existed = harepo.existsById(newAnswer.getId());
       if(existed)
       {
           return null;
       }
       else
       {
return harepo.save(newAnswer);
       }
    }

    @Override
    public Answer editAnswer(Answer updatedAnswer) {
        if(updatedAnswer == null)
        {
            return null;
        }
       Answer old = harepo.getById(updatedAnswer.getId());
        if(old==null)
        {
            return null;
        }
        else
        {
            old.setId(updatedAnswer.getId());
            old.setTitle(updatedAnswer.getTitle());
            old.setAnswerContent(updatedAnswer.getAnswerContent()+"(edited)");
            old.setUserId(updatedAnswer.getUserId());
            old.setIsActive(updatedAnswer.getIsActive());
            old.setIsPublic(updatedAnswer.getIsPublic());
            old.setRating(updatedAnswer.getRating());
            return harepo.save(old);
        }
    }

    @Override
    public void deleteAnswer(Answer answer) {
      harepo.delete(answer);
    }

    @Override
    public Answer getById(UUID answerId) {
        if(answerId==null)
        {
            return null;
        }
     Answer answer = harepo.findById(answerId).get();
     if(answer==null)
     {
         return null;
     }
     else
     {
         return answer;
     }
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
}
