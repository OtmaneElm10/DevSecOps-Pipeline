package com.eventapp.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventapp.model.entities.User;
import com.eventapp.model.service.AuthService;

/**
 * Controller for authentication endpoints.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
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
     * Registers a new user.
     *
     * @param body request body containing email and password
     * @return the created user
     */
    @PostMapping("/register")
    public User register(@RequestBody final Map<String, String> body) {
        return authService.register(body.get("email"), body.get("password"));
    }

    /**
     * Logs in a user.
     *
     * @param body request body containing email and password
     * @return the authenticated user
     */
    @PostMapping("/login")
    public User login(@RequestBody final Map<String, String> body) {
        return authService.login(body.get("username"), body.get("password"));
    }
}

