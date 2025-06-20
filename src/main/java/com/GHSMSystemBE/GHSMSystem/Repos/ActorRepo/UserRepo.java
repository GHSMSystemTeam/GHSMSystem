package com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo;

import com.GHSMSystemBE.GHSMSystem.Models.Role;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    List<User> findAll(Specification<User> spec);
    List<User> findByRoleAndIsActive(Role role, boolean status);
    List<User> findByRole(Role role);

    Optional<User> findById(UUID uuid);
}
