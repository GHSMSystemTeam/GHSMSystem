package com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepo extends JpaRepository<ServiceType,Integer> {
}
