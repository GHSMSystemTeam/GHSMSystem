package com.GHSMSystemBE.GHSMSystem.Repos;

import com.GHSMSystemBE.GHSMSystem.Dto.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface consultantRepo extends JpaRepository<Consultant,String> {
}
