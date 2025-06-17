package com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceType;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceBookingRepo extends JpaRepository<ServiceBooking,Integer> {
    List<ServiceBooking> findByIsActive(Boolean status);

    @Query("SELECT sb FROM ServiceBooking sb WHERE sb.customerId = :cusID")
    List<ServiceBooking> findByCustomerId(@Param("cusID") User u);

    @Query("SELECT sb FROM ServiceBooking sb WHERE sb.consultantId = :consID")
    List<ServiceBooking> findByConsultantId(@Param("consID") User u);

    @Query("SELECT sb FROM ServiceBooking sb WHERE sb.serviceTypeId = :serID")
    List<ServiceBooking> findByServiceTypeId(@Param("serID")ServiceType st);

    ServiceBooking findById(UUID id);
}
