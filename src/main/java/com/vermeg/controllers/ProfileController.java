package com.vermeg.controllers;

import com.vermeg.entities.User;
import com.vermeg.payload.responses.ApiResponse;
import com.vermeg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public ApiResponse<Object> getProfile(Principal principal){
        User userProfile = userService.getProfile(principal);
        String messageResponse = messageSource.getMessage("common.getProfile",
                null, LocaleContextHolder.getLocale());
        return new ApiResponse<>(200, messageResponse, userProfile);
    }

    @RequestMapping(value = "profile", method = RequestMethod.PUT, consumes = {"multipart/form-data"})
    public ApiResponse<Object> updateProfile(Principal principal,
         @RequestParam(value = "properties", required = false) User updatedUser,
         @RequestParam(value = "file", required = false) MultipartFile file){
        userService.updateProfile(principal, updatedUser, file);
        String messageResponse = messageSource.getMessage("common.updateProfile",
                null, LocaleContextHolder.getLocale());
        return new ApiResponse<>(200, messageResponse,null);
    }
}
