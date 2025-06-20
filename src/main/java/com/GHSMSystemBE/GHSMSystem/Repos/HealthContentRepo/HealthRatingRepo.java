package com.GHSMSystemBE.GHSMSystem.Repos.HealthContentRepo;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Feedback;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Rating;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthRatingRepo {
    List<Feedback> findAll(Specification<Rating> specification);
}
