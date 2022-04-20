package com.vermeg.service.impl;

import com.vermeg.entities.PasswordResetToken;
import com.vermeg.entities.User;
import com.vermeg.repositories.PasswordResetTokenRepository;
import com.vermeg.repositories.UserRepository;
import com.vermeg.service.ForgotPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ForgotPasswordServiceImpl implements ForgotPassword {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public void forgotPassword(String userEmail){
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
//            throw new UserNotFoundException();
        }
        // delete old token
        // Create a new token in the database
        String token = UUID.randomUUID().toString();
        PasswordResetToken createdToken = new PasswordResetToken(); // token, user
        createdToken.setToken(token);
        createdToken.setUser(user);
        //token, user
        passwordResetTokenRepository.save(createdToken);
        // send mail
        // return response
    }


    public void resetPassword(String token){

    }
}
