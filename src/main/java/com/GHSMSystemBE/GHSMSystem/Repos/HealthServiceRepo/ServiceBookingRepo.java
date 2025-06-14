package com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceBookingRepo extends JpaRepository<ServiceBooking,Integer> {
}
