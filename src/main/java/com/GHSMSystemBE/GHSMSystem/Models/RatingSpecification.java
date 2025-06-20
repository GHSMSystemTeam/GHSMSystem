package com.GHSMSystemBE.GHSMSystem.Models;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Rating;
import org.springframework.data.jpa.domain.Specification;

public class RatingSpecification {
    public static Specification<Rating> hasStatusTrue()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),true)));
    }
    public static Specification<Rating> hasStatusFalse()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),false)));
    }
}
