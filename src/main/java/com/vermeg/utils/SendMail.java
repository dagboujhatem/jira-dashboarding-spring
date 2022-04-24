package com.vermeg.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMail {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendEmail(String recipientEmail, String subject){
        System.out.println("Sending Email...");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(recipientEmail);
        mail.setSubject(subject);
        mail.setText("Hello World \n Spring Boot Email");
        // Send mail with javaMailSender
        javaMailSender.send(mail);
    }

}
