package com.GHSMSystemBE.GHSMSystem.Services.impl;

import com.GHSMSystemBE.GHSMSystem.Dto.User;
import com.GHSMSystemBE.GHSMSystem.Repos.userRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService implements IUserService {

    @Autowired
    userRepo repo;

    @Override
    public List<User> getAll() {
        return repo.findAll();
    }

    @Override
    public User getByEmail(String email) {
        return repo.findByEmail(email);
    }

    public User getByEmailAndPassword(String email, String password){
        return repo.findByEmailAndPassword(email, password);
    }

    @Override
    public User createUser(User u) {
        if(u != null){
            return repo.save(u);
        }
        return null;
    }

    @Override
    public User editUser(User u) {
        return null;
    }

    @Override
    public User checkLogin(String email, String password) {
        return null;
    }
}
