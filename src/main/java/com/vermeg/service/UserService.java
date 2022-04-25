package com.vermeg.service;

import com.vermeg.entities.User;
import com.vermeg.exceptions.EmailAlreadyUsedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.List;

public interface UserService {
    User findUserByEmail(String username);
    List<User> findAll();
    User save(User user) throws EmailAlreadyUsedException, MessagingException;
    User findById(int id);
    User update(int id, User updatedUser) throws EmailAlreadyUsedException;
    void delete(int id);
    // Profile section
    User getProfile(Authentication authentication);
    void updateProfile(Principal principal, User updatedUser, MultipartFile file) throws EmailAlreadyUsedException;
}
