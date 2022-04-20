package com.vermeg.controllers;

import com.vermeg.payload.responses.ApiResponse;
import com.vermeg.payload.responses.TokenResponse;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ForgotPasswordController {

    @RequestMapping(value = "forgot-password/{email}", method = RequestMethod.POST)
    public ApiResponse<TokenResponse> forgotPassword(@RequestParam("email") String findByEmail){
        return new ApiResponse<>(200, "success",null);
    }

    @RequestMapping(value = "reset-password", method = RequestMethod.POST)
    public ApiResponse<TokenResponse> resetPassword(){
        return new ApiResponse<>(200, "success",null);
    }
}
