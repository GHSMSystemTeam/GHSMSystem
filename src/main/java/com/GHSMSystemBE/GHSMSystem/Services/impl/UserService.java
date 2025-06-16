package com.GHSMSystemBE.GHSMSystem.Services.impl;

import com.GHSMSystemBE.GHSMSystem.Models.Role;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Models.UserSpecifications;
import com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo.roleRepo;
import com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo.userRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    private userRepo repo;
    @Autowired
    private roleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // get all existed user
    @Override
    public List<User> getAll() {
        return repo.findAll();
    }

    // get all user with active status (include Customer and Consultant)
    @Override
    public List<User> getAllActiveUser(){
        return repo.findAll(UserSpecifications.hasStatusTrue());
    }

    //get all customer
    @Override
    public List<User> getAllCustomer() {
        Role role = roleRepo.findById(1).orElseThrow();
        return repo.findByRole(role);
    }

    //get all consultant
    @Override
    public List<User> getAllConsultant() {
        Role role   = roleRepo.findById(2).orElseThrow();
        return repo.findByRole(role);
    }

    // get all user with role customer and active status
    @Override
    public List<User> getAllActiveCustomer() {
Integer roleId = 1;
        Role role = roleRepo.findById(roleId).orElseThrow();
        return repo.findByRoleAndIsActive(role, true);
    }

    // get all user with role consultant and active status
    @Override
    public List<User> getAllActiveConsultant() {
        Integer roleId = 2;
        Role role = roleRepo.findById(roleId).orElseThrow();
        return repo.findByRoleAndIsActive(role, true);
    }

    // get all user with role customer and deactive status
    @Override
    public List<User> getAllDeActiveCustomer() {
        Integer roleId = 1;
        Role role = roleRepo.findById(roleId).orElseThrow();
        return repo.findByRoleAndIsActive(role, false);
    }

    // get all user with role consultant and deactive status
    @Override
    public List<User> getAllDeActiveConsultant() {
        Integer roleId = 2;
        Role role = roleRepo.findById(roleId).orElseThrow();
        return repo.findByRoleAndIsActive(role, false);
    }

    @Override
    public void activeUser(User u) {

        u.setActive(true);
        repo.save(u);
    }

    @Override
    public void deActiveUser(User u) {

        u.setActive(false);
        repo.save(u);
    }

    // get user by user email
    @Override
    public User getByEmail(String email) {
        return repo.findByEmail(email);
    }

    // create a new user
    @Override
    public User createUser(User u) {

        // check user is existed ????
        User u1 = repo.findByEmail(u.getEmail());

        // if no user exist
        if(u1 == null){
            System.out.println("LOG:"+ u.getPassword());
            String uPass = u.getPassword();
            u.setPassword(passwordEncoder.encode(uPass));
            u.setCreateDate(LocalDateTime.now());
            u.setRole(roleRepo.findById(1).orElseThrow());
            return repo.save(u);
        }

        //if there is existed user
        return null;
    }

    @Override
    public void editUser(User u) {

        // check user is existed ????
        User u1 = repo.findByEmail(u.getEmail());

        if(u1 != null){
            u1.setName(u.getName());
            u1.setEmail(u.getEmail());
            String uPass = u.getPassword();
            u1.setPassword(passwordEncoder.encode(uPass));
            u1.setPhone(u.getPhone());
            repo.save(u1);
        }
    }

    @Override
    public User checkLogin(String email, String password) {
        User u = repo.findByEmail(email);
        if(u!=null &&  passwordEncoder.matches(password, u.getPassword()) && u.isActive())
        {
            return u;
        }
        return null;
    }

    @Override
    public User getById(String id) {
            Optional<User> oUser= repo.findById(UUID.fromString(id));
            if(oUser.isPresent()) {
                User foundUser = oUser.get();
                System.out.println("LOG: User found: " + foundUser);
                return foundUser;
            }
            else
            {
                return null;
            }
    }

    @Override
    public void deleteUser(User user) {
        repo.delete(user);
    }


}
