package com.GHSMSystemBE.GHSMSystem.Services.impl;

import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Models.UserSpecifications;
import com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo.userRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    userRepo repo;

    // get all existed user
    @Override
    public List<User> getAll() {
        return repo.findAll();
    }

    @Override
    public List<User> getAllActiveUser(){
        return repo.findAll(UserSpecifications.hasStatusTrue());
    }

    // get user by user email
    @Override
    public User getByEmail(String email) {
        return repo.findByEmail(email);
    }

    // get a user by email and password
    @Override
    public User getByEmailAndPassword(String email, String password){
        return repo.findByEmailAndPassword(email, password);
    }

    // create a new user
    @Override
    public User createUser(User u) {

        // check user is existed ????
        User u1 = repo.findByEmail(u.getEmail());

        // if no user exist
        if(u1 != null){
            return repo.save(u);
        }

        //if there is existed user
        return null;
    }

    @Override
    public User editUser(User u) {

        // check user is existed ????
        User u1 = repo.findByEmail(u.getEmail());

        if(u1 != null){
            u1.setName(u.getName());
            u1.setEmail(u.getEmail());
            u1.setPassword(u.getPassword());
            u1.setPhone(u.getPhone());
            u1.setBirthDate(u.getBirthDate());
            return repo.save(u1);
        }
        return null;
    }

    @Override
    public User checkLogin(String email, String password) {
        return repo.findByEmailAndPassword(email, password);
    }


}
