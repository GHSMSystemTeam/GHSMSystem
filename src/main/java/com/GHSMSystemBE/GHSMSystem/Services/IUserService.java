package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.User;

import java.util.List;

public interface IUserService {
    List<User> getAll();
    List<User> getAllActiveUser();
    User getByEmail(String email);
    User getByEmailAndPassword(String email, String password);
    User createUser(User u);
    User editUser(User u);
    User checkLogin(String email, String password);
    User getById(String id);
    void deleteUser(User user);
}
