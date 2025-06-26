package com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo;

import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceBooking;
import com.GHSMSystemBE.GHSMSystem.Models.HealthService.ServiceResult;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceResultRepo extends JpaRepository<ServiceResult, Integer> {

    @Query("SELECT sr FROM ServiceResult sr WHERE sr.customerId = :cusID")
    List<ServiceResult> findByCustomerId(@Param("cusID") User u);

    @Query("SELECT sr FROM ServiceResult sr WHERE sr.serviceBookingId = :sbID")
    List<ServiceResult> findByServiceBookingId(@Param("sbID") ServiceBooking sb);

    @Query("SELECT sr FROM ServiceResult sr JOIN sr.serviceBookingId sb WHERE sb.consultantId = :cID")
    List<ServiceResult> findByConsultantId(@Param("cID") User c);

    ServiceResult findById(UUID id);

}
