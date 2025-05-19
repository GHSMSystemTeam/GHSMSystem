package com.GHSMSystemBE.GHSMSystem.Repos;

import com.GHSMSystemBE.GHSMSystem.Dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends JpaRepository<User, String> {
}
