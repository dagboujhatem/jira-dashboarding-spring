package com.vermeg.controllers;

import com.vermeg.payload.responses.ApiResponse;
import com.vermeg.entities.User;
import com.vermeg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private UserService userService ;

    @PostMapping
    public ApiResponse<User> saveUser(@RequestBody User user){
        String modelName = messageSource.getMessage("models.user",null , LocaleContextHolder.getLocale());
        String messageResponse = messageSource.getMessage("common.create",
                new Object[] {modelName}, LocaleContextHolder.getLocale());
        return new ApiResponse<>(HttpStatus.OK.value(), messageResponse, userService.save(user));
    }

    @GetMapping
    public ApiResponse<List<User>> listUser(){
        String modelName = messageSource.getMessage("models.users",null , LocaleContextHolder.getLocale());
        String messageResponse = messageSource.getMessage("common.getAll",
                new Object[] {modelName}, LocaleContextHolder.getLocale());
        return new ApiResponse<>(HttpStatus.OK.value(), messageResponse, userService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getOne(@PathVariable int id){
        String modelName = messageSource.getMessage("models.user",null , LocaleContextHolder.getLocale());
        String messageResponse = messageSource.getMessage("common.getOne",
                new Object[] {modelName}, LocaleContextHolder.getLocale());
        return new ApiResponse<>(HttpStatus.OK.value(), messageResponse, userService.findById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<User> update(@RequestBody User updatedUser) {
        String modelName = messageSource.getMessage("models.user",null , LocaleContextHolder.getLocale());
        String messageResponse = messageSource.getMessage("common.update",
                new Object[] {modelName}, LocaleContextHolder.getLocale());
        return new ApiResponse<>(HttpStatus.OK.value(), messageResponse, userService.update(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable int id) {
        userService.delete(id);
        String modelName = messageSource.getMessage("models.user",null , LocaleContextHolder.getLocale());
        String messageResponse = messageSource.getMessage("common.delete",
                new Object[] {modelName}, LocaleContextHolder.getLocale());
        return new ApiResponse<>(HttpStatus.OK.value(), messageResponse, null);
    }



}
