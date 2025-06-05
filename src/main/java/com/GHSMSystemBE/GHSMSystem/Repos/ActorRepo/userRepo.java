package com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo;

import com.GHSMSystemBE.GHSMSystem.Models.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface userRepo extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    List<User> findAll(Specification<User> spec);
}
