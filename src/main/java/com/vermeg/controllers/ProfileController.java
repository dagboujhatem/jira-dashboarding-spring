package com.vermeg.controllers;

import com.vermeg.payload.responses.ApiResponse;
import com.vermeg.payload.responses.TokenResponse;
import com.vermeg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResponse<Object> getProfile(){
        return new ApiResponse<>(200, "success",null);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ApiResponse<Object> updateProfile(){
        return new ApiResponse<>(200, "success",null);
    }
}
