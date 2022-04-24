package com.vermeg.service;

import com.vermeg.entities.User;
import com.vermeg.exceptions.EmailAlreadyUsedException;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface UserService {
    User findUserByEmail(String username);
    List<User> findAll();
    User save(User user) throws EmailAlreadyUsedException;
    User findById(int id);
    User update(User updatedUser);
    void delete(int id);
    // Profile section
    User getProfile(Principal principal);
    void updateProfile(Principal principal, User updatedUser, MultipartFile file);
}
