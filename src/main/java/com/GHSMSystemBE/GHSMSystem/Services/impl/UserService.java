package com.GHSMSystemBE.GHSMSystem.Services.impl;

import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Models.UserSpecifications;
import com.GHSMSystemBE.GHSMSystem.Repos.ActorRepo.userRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private userRepo repo;
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

    // get all user with role customer and active status
    @Override
    public List<User> getAllActiveCustomer() {
        return repo.findByRoleAndIsActive(1, true);
    }

    // get all user with role consultant and active status
    @Override
    public List<User> getAllActiveConsultant() {
        return repo.findByRoleAndIsActive(2, true);
    }

    @Override
    public List<User> getAllDeActiveCustomer() {
        return repo.findByRoleAndIsActive(1, false);
    }

    @Override
    public List<User> getAllDeActiveConsultant() {
        return repo.findByRoleAndIsActive(2, false);
    }

    @Override
    public void deActiveCustomer(User u) {

    }

    @Override
    public void deActiveConsultant(User u) {

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
        User u = repo.findByEmailAndPassword(email, password);
        if(u!=null &&  passwordEncoder.matches(password, u.getPassword()))
        {
            return u;
        }
        return null;
    }

    @Override
    public User getById(String id) {

            Optional<User> oUser= repo.findById(id);
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
