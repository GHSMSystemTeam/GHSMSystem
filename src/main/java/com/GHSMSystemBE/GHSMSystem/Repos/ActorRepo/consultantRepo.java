package com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo;

import com.GHSMSystemBE.GHSMSystem.Models.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface consultantRepo extends JpaRepository<Consultant,String> {
}
