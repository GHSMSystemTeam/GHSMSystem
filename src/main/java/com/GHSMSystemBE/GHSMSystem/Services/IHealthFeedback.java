package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.FeedbackDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Feedback;
import java.util.List;

public interface IHealthFeedback {
    List<Feedback> getAll();
    List<Feedback> getAllActiveFeedbacks();
    List<Feedback> getAllInactiveFeedbacks();
    Feedback createFeedback(FeedbackDTO dto);
    Feedback editFeedback(FeedbackDTO dto, String id);
    void deleteFeedback(String id);
    Feedback getById(String id);
    void active(String id);
    void deActive(String id);
}