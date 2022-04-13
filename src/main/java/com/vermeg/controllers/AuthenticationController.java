package com.vermeg.controllers;

import com.vermeg.payload.responses.TokenResponse;
import com.vermeg.security.JwtTokenUtil;
import com.vermeg.entities.*;
import com.vermeg.payload.requests.LoginRequest;
import com.vermeg.payload.responses.ApiResponse;
import com.vermeg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ApiResponse<TokenResponse> login(@RequestBody LoginRequest loginRequest) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        final User user = userService.findUserByEmail(loginRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "success",new TokenResponse(token, user.getEmail()));
    }

}
