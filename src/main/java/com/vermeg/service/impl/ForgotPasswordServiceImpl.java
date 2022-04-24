package com.vermeg.service.impl;

import com.vermeg.entities.PasswordResetToken;
import com.vermeg.entities.User;
import com.vermeg.exceptions.BadRequestException;
import com.vermeg.repositories.PasswordResetTokenRepository;
import com.vermeg.repositories.UserRepository;
import com.vermeg.service.ForgotPassword;
import com.vermeg.utils.Email;
import com.vermeg.utils.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
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

    @Autowired
    MessageSource messageSource;

    @Autowired
    EmailSenderService emailSender;

    public void forgotPassword(String userEmail) throws MessagingException, BadRequestException {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new BadRequestException("This e-mail address is not registered with us.");
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
        Email email = new Email();
        email.setTo(userEmail);
        email.setFrom("svermeg@gmail.com");
        String message = messageSource.getMessage("resetPasswordMailHeader",
                null, LocaleContextHolder.getLocale());
        email.setSubject(message);
        email.setTemplate("forgot-password.html");
        Map<String, Object> prop = new HashMap<>();
        prop.put("name", user.getFirstName() + " " + user.getLastName());
        email.setProperties(prop);
        emailSender.sendHtmlMessage(email);
    }


    public void resetPassword(String token){

    }
}
