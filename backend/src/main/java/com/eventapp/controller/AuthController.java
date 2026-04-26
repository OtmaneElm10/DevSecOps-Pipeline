package com.eventapp.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventapp.model.dto.LoginRequestDto;
import com.eventapp.model.dto.RegisterRequestDto;
import com.eventapp.model.entities.User;
import com.eventapp.model.service.AuthService;

/**
 * Controller for authentication endpoints.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Constructs the authentication controller.
     *
     * @param authService the authentication service
     */
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    /**
    * Register a new user.
    *
    * @param  request request body containing email, password and username.
    * @return the created user
    */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody final RegisterRequestDto request) {
        
        User user = authService.register(
            request.getUsername(),
            request.getEmail(),
            request.getPassword()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
    * Logs in a user.
    *
    * @param request request body containing username and password
    * @return the authenticated user
    */
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody final LoginRequestDto request) {
        User user = authService.login(
            request.getUsername(),
            request.getPassword()
        );
        return ResponseEntity.ok(user);
            
    }

}

