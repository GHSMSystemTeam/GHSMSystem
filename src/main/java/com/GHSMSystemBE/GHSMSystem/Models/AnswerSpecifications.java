package com.GHSMSystemBE.GHSMSystem.Models;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Answer;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Question;
import org.springframework.data.jpa.domain.Specification;

public class AnswerSpecifications {
    public static Specification<Answer> hasStatusTrue()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),true)));
    }
    public static Specification<Answer> hasStatusFalse()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),false)));
    }
    public static Specification<Answer> madeByUser(User user)
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"),user)));
    }
    public static Specification<Answer> getByQuestionId(Question question)
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("question"),question)));
    }
}
