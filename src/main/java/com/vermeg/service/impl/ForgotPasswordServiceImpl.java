package com.vermeg.service.impl;

import com.vermeg.entities.PasswordResetToken;
import com.vermeg.entities.User;
import com.vermeg.exceptions.ResourceNotFoundException;
import com.vermeg.repositories.PasswordResetTokenRepository;
import com.vermeg.repositories.UserRepository;
import com.vermeg.service.ForgotPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
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
            throw new ResourceNotFoundException("This e-mail address is not registered with us.");
        }
        // delete old token
//        Optional<PasswordResetToken> oldToken = passwordResetTokenRepository.findByUser(user);
//        if(oldToken.isPresent()){
//            passwordResetTokenRepository.delete(oldToken.get());
//        }
        // Create a new token in the database
        String token = UUID.randomUUID().toString();
        PasswordResetToken createdToken = new PasswordResetToken();
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
