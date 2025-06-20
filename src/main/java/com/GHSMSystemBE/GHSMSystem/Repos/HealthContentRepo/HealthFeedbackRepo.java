package com.GHSMSystemBE.GHSMSystem.Repos.HealthContentRepo;

import com.GHSMSystemBE.GHSMSystem.Models.FeedbackSpecification;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Feedback;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HealthFeedbackRepo extends JpaRepository<Feedback, UUID> {
    List<Feedback> findAll(Specification<Feedback> specification);
}
