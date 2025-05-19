package com.GHSMSystemBE.GHSMSystem.Repos;

import com.GHSMSystemBE.GHSMSystem.Dto.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface staffRepo extends JpaRepository<Staff, String> {
}
