package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthContent;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.RatingDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Rating;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Models.ModelSpecification.RatingSpecification;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthContentRepo.HealthRatingRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IBookingService;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthRating;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RatingService implements IHealthRating {

    @Autowired
    private HealthRatingRepo hrRepo;
    @Autowired
    private IUserService uService;
    @Autowired
    private IBookingService sbService;

    @Override
    public List<Rating> getAll() {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Getting all ratings");
        return hrRepo.findAll();
    }

    @Override
    public List<Rating> getAllActiveRatings() {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Getting all active ratings");
        return hrRepo.findAll(RatingSpecification.hasStatusTrue());
    }

    @Override
    public List<Rating> getAllInactiveRatings() {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Getting all inactive ratings");
        return hrRepo.findAll(RatingSpecification.hasStatusFalse());
    }

    @Override
    public Rating createRating(RatingDTO dto) {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Creating new rating");
        Rating rating = new Rating();

        // Retrieve and validate related entities
        User customer = uService.getById(dto.getCustomerId());
        if (customer == null) {
            System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Customer not found with ID: " + dto.getCustomerId());
            return null;
        }
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Retrieved customer: " + customer.getId());

        User consultant = uService.getById(dto.getConsultantId());
        if (consultant == null) {
            System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Consultant not found with ID: " + dto.getConsultantId());
            return null;
        }
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Retrieved consultant: " + consultant.getId() + ", current avgRating: " + consultant.getAvgRating());

        ServiceBooking sb = sbService.getById(dto.getServiceBookingId());
        if (sb == null) {
            System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Service booking not found with ID: " + dto.getServiceBookingId());
            return null;
        }
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Retrieved booking: " + sb.getId());

        // Set rating properties
        rating.setCustomerId(customer);
        rating.setConsultantId(consultant);
        rating.setServiceBookingId(sb);
        rating.setTitle(dto.getTitle());
        rating.setContent(dto.getContent());
        rating.setRating(dto.getRating());
        rating.setIsPublic(dto.getIsPublic());
        rating.setCreateDate(dto.getCreateDate() != null ? dto.getCreateDate() : LocalDateTime.now());
        rating.setIsActive(true); // Ensure rating is active by default

        // Save and update consultant average rating
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Saving rating with value: " + dto.getRating());
        Rating saved = hrRepo.save(rating);
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Rating saved with ID: " + saved.getId());

        User updatedConsultant = setAvgRating(consultant);
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - After setAvgRating, consultant avgRating: " + updatedConsultant.getAvgRating());

        return saved;
    }

    @Override
    public Rating editRating(RatingDTO dto, String id) {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Editing rating with ID: " + id);
        Rating old = getById(id);
        if (old == null) {
            System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Rating not found with ID: " + id);
            return null;
        }
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Found rating with old value: " + old.getRating());

        // Only append "(edited)" if it's not already there
        String newTitle = dto.getTitle();
        if (!newTitle.endsWith("(edited)")) {
            newTitle += "(edited)";
        }
        old.setTitle(newTitle);

        String newContent = dto.getContent();
        if (!newContent.endsWith("(edited)")) {
            newContent += "(edited)";
        }
        old.setContent(newContent);

        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Updating rating from " + old.getRating() + " to " + dto.getRating());
        old.setRating(dto.getRating());
        old.setIsPublic(dto.getIsPublic());

        Rating saved = hrRepo.save(old);
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Rating updated and saved");

        User consultant = saved.getConsultantId();
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Updating avgRating for consultant: " + consultant.getId() + ", current avgRating: " + consultant.getAvgRating());

        User updatedConsultant = setAvgRating(consultant);
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - After setAvgRating, consultant avgRating: " + updatedConsultant.getAvgRating());

        return saved;
    }

    @Override
    public Rating deleteRating(String id) {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Deleting rating with ID: " + id);
        Rating delete = getById(id);
        if (delete == null) {
            System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Rating not found with ID: " + id);
            return null;
        }
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Found rating with value: " + delete.getRating());

        User consultant = delete.getConsultantId();
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Deleting rating for consultant: " + consultant.getId() + ", current avgRating: " + consultant.getAvgRating());

        hrRepo.delete(delete);
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Rating deleted successfully");

        User updatedConsultant = setAvgRating(consultant);
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - After setAvgRating, consultant avgRating: " + updatedConsultant.getAvgRating());

        return delete;
    }

    @Override
    public Rating getById(String id) {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Getting rating by ID: " + id);
        try {
            UUID uuid = UUID.fromString(id);
            Optional<Rating> oRating = hrRepo.findById(uuid);
            return oRating.orElse(null);
        } catch (IllegalArgumentException e) {
            System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Invalid UUID format: " + id);
            return null;
        }
    }

    @Override
    public Rating active(String id) {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Activating rating with ID: " + id);
        Rating rating = getById(id);
        if(rating == null) {
            System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Rating not found with ID: " + id);
            return null;
        }
        rating.setIsActive(true);
        Rating saved = hrRepo.save(rating);
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Rating activated and saved");
        return saved;
    }

    @Override
    public Rating deActive(String id) {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Deactivating rating with ID: " + id);
        Rating rating = getById(id);
        if(rating == null) {
            System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Rating not found with ID: " + id);
            return null;
        }
        rating.setIsActive(false);
        Rating saved = hrRepo.save(rating);
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Rating deactivated and saved");
        return saved;
    }

    @Override
    public List<Rating> findByUser(String userId) {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Finding ratings by user ID: " + userId);
        User user = uService.getById(userId);
        List<Rating> list = hrRepo.findAll(RatingSpecification.findByUser(user));
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Found " + list.size() + " ratings for user");
        return list;
    }

    @Override
    public List<Rating> findByServiceBooking(String sbId) {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Finding ratings by service booking ID: " + sbId);
        ServiceBooking sb = sbService.getById(sbId);
        List<Rating> list = hrRepo.findAll(RatingSpecification.findByServiceBooking(sb));
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Found " + list.size() + " ratings for service booking");
        return list;
    }

    @Override
    public List<Rating> findInRange(Float min, Float max) {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Finding ratings in range: " + min + " to " + max);
        List<Rating> list = hrRepo.findAll(RatingSpecification.hasRatingBetween(min,max));
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Found " + list.size() + " ratings in range");
        return list;
    }

    @Override
    public List<Rating> findForConsultantInRange(Float min, Float max, String consultantId) {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Finding ratings for consultant ID: " + consultantId + " in range: " + min + " to " + max);
        User consultant = uService.getById(consultantId);
        List<Rating> list = hrRepo.findAll(RatingSpecification.hasRatingBetween(min,max).and(RatingSpecification.findByConsultant(consultant)));
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Found " + list.size() + " ratings for consultant in range");
        return list;
    }

    @Override
    public Float calculateAvgRatingForConsultant(User consultant) {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Calculating average rating for consultant: " + consultant.getId());
        Float avgRating = 0f;

        List<Rating> list = hrRepo.findAll(RatingSpecification.findByConsultant(consultant));
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Found " + list.size() + " ratings for consultant");

        if(list.isEmpty()) {
            System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - No ratings found, returning 0");
            return avgRating;
        } else {
            Float sum = 0f;
            System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Individual ratings:");
            for(Rating rating : list) {
                System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Rating ID: " + rating.getId() + ", Value: " + rating.getRating());
                sum += rating.getRating();
            }
            System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Sum of ratings: " + sum);
            System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Number of ratings: " + list.size());

            avgRating = sum / list.size();
            System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Calculated average rating: " + avgRating);
            return avgRating;
        }
    }

    @Override
    public User setAvgRating(User consultant) {
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Setting average rating for consultant: " + consultant.getId());
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Consultant current avgRating: " + consultant.getAvgRating());

        Float avgRating = calculateAvgRatingForConsultant(consultant);
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - New calculated avgRating: " + avgRating);

        consultant.setAvgRating(avgRating);
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - Set consultant avgRating to: " + consultant.getAvgRating());

        uService.editUser(consultant);
        User updated = consultant;
        System.out.println("LOG: 2025-06-30 14:31:30 - TranDucHai2123 - After editUser, consultant avgRating: " + updated.getAvgRating());

        return updated;
    }
}