package com.GHSMSystemBE.GHSMSystem.Repos.ServiceRepo;

import com.GHSMSystemBE.GHSMSystem.Models.Heathservice.ServiceBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceAppointmentBookingRepo extends JpaRepository<ServiceBooking,Integer> {
}
