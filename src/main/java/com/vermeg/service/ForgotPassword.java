package com.vermeg.service;

import com.vermeg.exceptions.BadRequestException;
import com.vermeg.payload.responses.ApiResponse;
import com.vermeg.payload.responses.TokenResponse;

import javax.mail.MessagingException;

public interface ForgotPassword {
    public void forgotPassword(String userEmail) throws MessagingException, BadRequestException;
    public void resetPassword(String token);
}
