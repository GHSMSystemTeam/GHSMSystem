package com.GHSMSystemBE.GHSMSystem.Models.ModelSpecification;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Feedback;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import org.springframework.data.jpa.domain.Specification;

public class FeedbackSpecification {
    public static Specification<Feedback> hasStatusTrue()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),true)));
    }
    public static Specification<Feedback> hasStatusFalse()
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),false)));
    }

    public static Specification<Feedback> madeByCustomer(User user)
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customerId"),user)));
    }

    public static Specification<Feedback> findByServiceBooking(ServiceBooking sb)
    {
        return (( (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("serviceBookingId"),sb)));
    }
}
