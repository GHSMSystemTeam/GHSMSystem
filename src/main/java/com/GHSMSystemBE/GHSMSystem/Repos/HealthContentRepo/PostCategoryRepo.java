package com.GHSMSystemBE.GHSMSystem.Repos.HealthContentRepo;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.PostCategory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCategoryRepo extends JpaRepository<PostCategory,Integer> {
    List<PostCategory> findAll(Specification<PostCategory> specification);
}
