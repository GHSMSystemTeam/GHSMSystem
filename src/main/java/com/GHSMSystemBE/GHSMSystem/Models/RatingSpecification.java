package com.GHSMSystemBE.GHSMSystem.Models;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Rating;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import org.springframework.data.jpa.domain.Specification;

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

}
