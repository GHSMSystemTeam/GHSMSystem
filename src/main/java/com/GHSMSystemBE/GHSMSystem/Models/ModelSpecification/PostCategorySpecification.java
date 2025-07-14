package com.GHSMSystemBE.GHSMSystem.Models.ModelSpecification;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.PostCategory;
import org.springframework.data.jpa.domain.Specification;

public class PostCategorySpecification {

    public static Specification<PostCategory> hasStatusTrue()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),true)));
    }

    public static Specification<PostCategory> hasStatusFalse()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),false)));
    }


}
