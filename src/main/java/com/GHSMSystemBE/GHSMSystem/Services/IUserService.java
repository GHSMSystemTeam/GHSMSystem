package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Dto.User;

import java.util.List;

public interface IUserService {
    List<User> getAll();
    User getByEmail(String email);
    User getByEmailAndPassword(String email, String password);
    User createUser(User u);
    User editUser(User u);
    User checkLogin(String email, String password);
}
