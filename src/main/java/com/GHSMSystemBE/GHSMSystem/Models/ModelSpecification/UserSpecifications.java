package com.GHSMSystemBE.GHSMSystem.Models.ModelSpecification;

import com.GHSMSystemBE.GHSMSystem.Models.Role;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> hasStatusTrue(){
        return (root, query, builder) -> builder.equal(root.get("isActive"), true);
    }

    public static Specification<User> hasStatusFalse(){
        return (root, query, builder) -> builder.equal(root.get("isActive"), false);
    }

    public static Specification<User> getAllByRole(Role role)
    {
        return (root, query, builder) -> builder.equal(root.get("role"), role);
    }

    public static Specification<User> getAllByRoleActive(Role role)
    {
        return getAllByRole(role).and(hasStatusTrue());
    }

    public static Specification<User> getAllByRoleInActive(Role role)
    {
        return getAllByRole(role).and(hasStatusFalse());
    }



}
