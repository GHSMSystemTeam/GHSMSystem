package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthContent;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.RatingDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Rating;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Models.RatingSpecification;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo.UserRepo;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthContentRepo.HealthRatingRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IBookingService;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthRating;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RatingService implements IHealthRating {

    @Autowired
    private HealthRatingRepo hrRepo;
    @Autowired
    private IUserService uService;
    @Autowired
    private IBookingService sbService;
    @Override
    public List<Rating> getAll() {
        return hrRepo.findAll();
    }

    @Override
    public List<Rating> getAllActiveRatings() {
       return hrRepo.findAll(RatingSpecification.hasStatusTrue());
    }

    @Override
    public List<Rating> getAllInactiveRatings() {
        return hrRepo.findAll(RatingSpecification.hasStatusFalse());
    }

    @Override
    public Rating createRating(RatingDTO dto) {
        return null;
    }

    @Override
    public Rating editRating(RatingDTO dto, String id) {
        return null;
    }

    @Override
    public Rating deleteRating(String id) {
        return null;
    }

    @Override
    public Rating getById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Rating> oRating = hrRepo.findById(uuid);
        if(oRating ==null)
        {
           return null;
        }
        Rating rating = oRating.get();
        return  rating;

    }

    @Override
    public Rating active(String id) {
      Rating rating = getById(id);
      if(rating ==null)
      {
          return null;
      }
      rating.setIsActive(true);
      return rating;
    }

    @Override
    public Rating deActive(String id) {
        Rating rating = getById(id);
        if(rating ==null)
        {
            return null;
        }
        rating.setIsActive(false);
        return rating;
    }

    @Override
    public List<Rating> findByUser(String userId) {
        User user = uService.getById(userId);
        List<Rating> list = hrRepo.findAll(RatingSpecification.findByUser(user));
        return list;
    }

    @Override
    public List<Rating> findByServiceBooking(String sbId) {
        ServiceBooking sb = sbService.getById(sbId);
        List<Rating> list = hrRepo.findAll(RatingSpecification.findByServiceBooking(sb));
        return list;
    }

    @Override
    public List<Rating> findInRange(Float min, Float max) {
        return List.of();
    }

    @Override
    public List<Rating> findForConsultantInRange(Float min, Float max, String consultantId) {
        return List.of();
    }
}
