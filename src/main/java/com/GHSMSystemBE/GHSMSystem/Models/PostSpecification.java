package com.GHSMSystemBE.GHSMSystem.Models;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Post;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.PostCategory;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {
    public static Specification<Post> hasStatusTrue()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),true)));
    }
    public static Specification<Post> hasStatusFalse()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),false)));
    }

    public static Specification<Post> madeByConsultant(User consultantId)
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("consultantId"),consultantId)));
    }

    public static Specification<Post> havePostCategory(PostCategory category)
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("categoryId"),category)));
    }
}
