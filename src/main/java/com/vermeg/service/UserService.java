package com.vermeg.service;

import com.vermeg.entities.User;

import java.util.List;

public interface UserService {
    User findUserByEmail(String username);
    List<User> findAll();
    User save(User user);
    User findById(int id);
    User update(User updatedUser);
    void delete(int id);
    // Profile section
    User getProfile();
    void updateProfile();
}
