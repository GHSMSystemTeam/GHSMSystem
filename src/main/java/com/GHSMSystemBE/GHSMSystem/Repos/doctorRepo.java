package com.GHSMSystemBE.GHSMSystem.Repos;

import com.GHSMSystemBE.GHSMSystem.Dto.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface doctorRepo extends JpaRepository<Doctor, String> {
}
