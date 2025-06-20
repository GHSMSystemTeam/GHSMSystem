package com.GHSMSystemBE.GHSMSystem.Models;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Feedback;
import org.springframework.data.jpa.domain.Specification;

public class FeedbackSpecification {
    public static Specification<Feedback> hasStatusTrue()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),true)));
    }
    public static Specification<Feedback> hasStatusFalse()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),false)));
    }
}
