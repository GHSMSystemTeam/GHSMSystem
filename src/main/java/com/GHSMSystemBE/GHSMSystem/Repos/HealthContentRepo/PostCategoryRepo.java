package com.GHSMSystemBE.GHSMSystem.Repos.HealthContentRepo;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.PostCategory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCategoryRepo extends JpaRepository<PostCategory,String> {
    List<PostCategory> findAll(Specification<PostCategory> specification);
}
