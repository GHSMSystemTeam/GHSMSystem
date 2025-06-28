package com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo;

import com.GHSMSystemBE.GHSMSystem.Models.Role;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    List<User> findAll(Specification<User> spec);
    List<User> findByRoleAndIsActive(Role role, boolean status);
    List<User> findByRole(Role role);

    @Query("SELECT c FROM User c WHERE c.role = :rID AND NOT EXISTS (" +
            "SELECT b FROM ServiceBooking b WHERE b.consultantId = c AND " +
            "b.appointmentDate = :bd AND b.slot = :bs)")
    List<User> findConsultantNotBooked(@Param("rID") Role role,
                                       @Param("bd") Date bookingDate,
                                       @Param("bs") int bookingSlot);

    Optional<User> findById(UUID uuid);
}
