package com.vermeg.service;

import com.vermeg.entities.User;

import java.util.List;

public interface UserService {

    User save(User user);
    List<User> findAll();
    void delete(int id);

    User findOne(String username);

    User findById(int id);

    User update(User userDto);
}
