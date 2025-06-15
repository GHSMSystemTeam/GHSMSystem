package com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceTypeRepo extends JpaRepository<ServiceType,Integer> {
    List<ServiceType> findAll(Specification<ServiceType> spec);
}
