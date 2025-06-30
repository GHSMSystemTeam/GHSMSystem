package com.GHSMSystemBE.GHSMSystem.Models;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Rating;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class RatingSpecification {
    public static Specification<Rating> hasStatusTrue()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),true)));
    }
    public static Specification<Rating> hasStatusFalse()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),false)));
    }
    public static Specification<Rating> findByUser(User user)
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customerId"),user)));
    }

    public static Specification<Rating> findByServiceBooking(ServiceBooking sb)
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("serviceBookingId"),sb)));
    }

    public static Specification<Rating>hasRatingBetween(Float min, Float max)
    {
        return (( ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("rating"),min,max))));
    }

    public static Specification<Rating>createdAfter(LocalDateTime createDate)
    {
        return (( ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"),createDate))));
    }

    public static Specification<Rating> findByConsultant(User consultant)
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("consultantId"),consultant)));
    }

}
