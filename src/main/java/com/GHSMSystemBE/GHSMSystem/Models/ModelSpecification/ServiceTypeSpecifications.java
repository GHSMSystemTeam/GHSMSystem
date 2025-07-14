package com.GHSMSystemBE.GHSMSystem.Models.ModelSpecification;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceType;
import org.springframework.data.jpa.domain.Specification;

public class ServiceTypeSpecifications {
    public static Specification<ServiceType> hasStatusTrue()
    {
        return((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),true));
    }
    public static Specification<ServiceType> hasStatusFalse()
    {
        return((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"),false));
    }

    public static Specification<ServiceType> findByTypeCode(Integer serviceTypeCode)
    {
        return((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("typeCode"),serviceTypeCode));
    }
}
