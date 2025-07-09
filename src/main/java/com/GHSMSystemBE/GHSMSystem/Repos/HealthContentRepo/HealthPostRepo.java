package com.GHSMSystemBE.GHSMSystem.Repos.HealthContentRepo;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Post;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HealthPostRepo extends JpaRepository<Post,UUID> {
    public List<Post> findAll(Specification<Post> specification);
}
