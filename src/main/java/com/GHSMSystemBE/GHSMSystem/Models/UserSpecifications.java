package com.GHSMSystemBE.GHSMSystem.Models;

import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> hasStatusTrue(){
        return (root, query, builder) -> builder.equal(root.get("isActive"), true);
    }

    public static Specification<User> hasStatusFalse(){
        return (root, query, builder) -> builder.equal(root.get("isActive"), false);
    }
}
