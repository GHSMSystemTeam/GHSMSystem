package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.RatingDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Rating;

import java.util.List;

public interface IHealthRating {
    List<Rating> getAll();
    List<Rating> getAllActiveRatings();
    List<Rating> getAllInactiveRatings();
    Rating createRating(RatingDTO dto);
    Rating editRating(RatingDTO dto, String id);
    Rating deleteRating(String id);
    Rating getById(String id);
    Rating active(String id);
    Rating deActive(String id);
    List<Rating> findByUser(String userId);
    List<Rating> findByServiceBooking(String sbId);
    List<Rating> findInRange(Float min, Float max);
    List<Rating> findForConsultantInRange(Float min, Float max, String consultantId);
}
