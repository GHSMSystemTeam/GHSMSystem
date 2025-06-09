package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.User;

import java.util.List;

public interface IUserService {
    List<User> getAll();
    List<User> getAllActiveUser();
    List<User> getAllCustomer();
    List<User> getAllConsultant();
    List<User> getAllActiveCustomer();
    List<User> getAllActiveConsultant();
    List<User> getAllDeActiveCustomer();
    List<User> getAllDeActiveConsultant();
    void activeUser(User u);
    void deActiveUser(User u);
    User getByEmail(String email);
    User createUser(User u);
    void editUser(User u);
    User checkLogin(String email, String password);
    User getById(String id);
    void deleteUser(User user);
}
