package com.GHSMSystemBE.GHSMSystem.Models;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Question;
import org.springframework.data.jpa.domain.Specification;

public class QuestionSpecifications {
    public static Specification<Question> hasStatusTrue()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),true)));
    }
    public static Specification<Question> hasStatusFalse()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),false)));
    }

    public static Specification<Question> madeByUser(User user)
    {
        return ((((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customer"), user))));
    }



}
