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
    Feedback deleteFeedback(String id);
    Feedback getById(String id);
    Feedback active(String id);
    Feedback deActive(String id);
    List<Feedback> findByUser(String userId);
    List<Feedback> findByServiceBooking(String sbId);
}