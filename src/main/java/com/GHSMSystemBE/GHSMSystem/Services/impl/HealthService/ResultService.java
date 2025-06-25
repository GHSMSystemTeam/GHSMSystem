package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthService;

import com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo.UserRepo;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthServiceRepo.ServiceResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService {

    @Autowired
    private ServiceResultRepo srrepo;
    @Autowired
    private UserRepo urepo;
}
