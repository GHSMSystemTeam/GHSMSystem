package com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceResultRepo extends JpaRepository<ServiceResult, UUID> {
}
