package com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo;

import com.GHSMSystemBE.GHSMSystem.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface roleRepo extends JpaRepository<Role, Integer> {
}
