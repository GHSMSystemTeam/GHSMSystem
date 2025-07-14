package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthContent;


import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Question;
import com.GHSMSystemBE.GHSMSystem.Models.ModelSpecification.QuestionSpecifications;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthContentRepo.HealthQuestionRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthQuestion;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionService implements IHealthQuestion {
    @Autowired
   private HealthQuestionRepo hqrepo;

    @Autowired
    private IUserService userService;

    @Override
    public List<Question> getAll() {
        return hqrepo.findAll();
    }

    @Override
    public List<Question> getAllActiveQuestions() {
     return hqrepo.findAll(QuestionSpecifications.hasStatusTrue());
    }

    @Override
    public List<Question> getAllInactiveQuestions() {
        return hqrepo.findAll(QuestionSpecifications.hasStatusFalse());
    }

    @Override
    public Question createQuestion(Question newQuestion) {
        if (newQuestion.getId() == null) {
            return hqrepo.save(newQuestion);
        }
        Boolean exist = hqrepo.existsById(newQuestion.getId());
        if (exist) {
            return null;
        } else {
            return hqrepo.save(newQuestion);
        }
    }

    @Override
    public Question editQuestion(Question updatedQuestion) {
        Question old = hqrepo.findById(updatedQuestion.getId()).get();
        if(old == null)
        {
            return null;
        }
        else
        {
            old.setId(updatedQuestion.getId());
            old.setCustomer(updatedQuestion.getCustomer());
            old.setTitle(updatedQuestion.getTitle());
            old.setContent(updatedQuestion.getContent()+"(edited)");
            old.setAnswers(updatedQuestion.getAnswers());
            old.setIsPublic(updatedQuestion.getIsPublic());
            old.setIsActive(updatedQuestion.getIsActive());
        }
        return hqrepo.save(old);
    }

    @Override
    public void deleteQuestion(Question deleteQuestion) {
hqrepo.delete(deleteQuestion);
    }

    @Override
    public Question getById(String questionId) {
        if(questionId==null)
        {
            return null;
        }
        UUID uuid = UUID.fromString(questionId);
        Boolean exist = hqrepo.existsById(uuid);
        if(!exist)
        {
            return null;
        }
        else
        {
            Question found =  hqrepo.findById(uuid).get();
            return found;
        }
    }

    @Override
    public void active(Question question) {
       question.setIsActive(true);
       hqrepo.save(question);

    }

    @Override
    public void deActive(Question question) {
        question.setIsActive(false);
        hqrepo.save(question);
    }

    @Override
    public List<Question> getByUser(String userId) {
       User user = userService.getById(userId);
       if(user==null)
       {
           return null;
       }
       else {
           List<Question> list = hqrepo.findAll(QuestionSpecifications.madeByUser(user));
           return list;
       }
    }
}
