package com.GHSMSystemBE.GHSMSystem.Repos;

import com.GHSMSystemBE.GHSMSystem.Dto.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface adminRepo extends JpaRepository<Admin,String> {
}
