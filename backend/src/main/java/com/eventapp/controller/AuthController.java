package com.eventapp.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventapp.model.dto.AuthResponseDto;
import com.eventapp.model.dto.LoginRequestDto;
import com.eventapp.model.dto.RegisterRequestDto;
import com.eventapp.model.entities.User;
import com.eventapp.model.service.AuthService;
import com.eventapp.security.JwtService;

/**
 * Controller for authentication endpoints.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    /**
     * Constructs the authentication controller.
     *
     * @param authService the authentication service
     */
    public AuthController(final AuthService authService,
        final JwtService jwtService
    ) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    /**
    * Register a new user.
    *
    * @param  request request body containing email, password and username.
    * @return the created user
    */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody final RegisterRequestDto request) {
        
        User user = authService.register(
            request.getUsername(),
            request.getEmail(),
            request.getPassword()
        );
        
        AuthResponseDto response = new AuthResponseDto(
            jwtService.generateToken(user), 
            user.getId(), 
            user.getUsername(), 
            user.getEmail(), 
            user.getRole()
        );
        
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
    * Logs in a user.
    *
    * @param request request body containing username and password
    * @return the authenticated user
    */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody final LoginRequestDto request) {
        User user = authService.login(
            request.getUsername(),
            request.getPassword()
        );
        
        String token = jwtService.generateToken(user);

        AuthResponseDto response = new AuthResponseDto(
            token, 
            user.getId(), 
            user.getUsername(), 
            user.getEmail(), 
            user.getRole()
        );
            
        return ResponseEntity.ok(response);
    }

}

