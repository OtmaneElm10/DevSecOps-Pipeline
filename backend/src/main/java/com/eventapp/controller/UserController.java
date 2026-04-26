package com.eventapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventapp.model.entities.User;
import com.eventapp.model.service.UserService;

/**
 * User controller.
 * Handles HTTP requests related to users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    /**
     * Constructs the user controller.
     *
     * @param userService the user service
     */
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    
    /**
     * Retrieves a user by their username.
     * @param username
     * @return
     */
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable final  String username) {
        User user = userService.getByUsername(username);
        return ResponseEntity.ok(user);
    }
}
