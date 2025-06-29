package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.Role;
import com.GHSMSystemBE.GHSMSystem.Models.User;

import java.sql.Date;
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
    List<User> getAllAvailableConsultant(Date bd, int slot);
    void activeUser(User u);
    void deActiveUser(User u);
    User getByEmail(String email);
    User createUser(User u);
    User createConsultant(User u);
    User createStaff(User u);
    void editUser(User u);
    void editUserPassword (User u, String password);
    User checkLogin(String email, String password);
    User getById(String id);
    void deleteUser(User user);
    User updateRole(String userId,Integer roleId);
    List<User> getAllActiveStaff();
    List<User> getAllStaff();
    List<User> getAllInactiveStaff();

}
