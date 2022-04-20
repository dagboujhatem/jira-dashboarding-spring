package com.vermeg.service;

import com.vermeg.payload.responses.ApiResponse;
import com.vermeg.payload.responses.TokenResponse;

public interface ForgotPassword {
    public void forgotPassword(String userEmail);
    public void resetPassword(String token);
}
