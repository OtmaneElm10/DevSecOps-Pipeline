package com.eventapp.model.service;

import org.springframework.stereotype.Service;

import com.eventapp.model.entities.User;
import com.eventapp.repositories.UserRepository;

/**
 * Service for authentication features.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;

    /**
     * Constructs the authentication service.
     *
     * @param userRepository the user repository
     */
    public AuthService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user.
     *
     * @param username username 
     * @param password the user password
     * @return the saved user
     */
    public User register(final String username, final String email, 
        final  String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username déjà utilisé");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("USER");

        return userRepository.save(user);
    }

    /**
     * Logs in a user.
     *
     * @param username username 
     * @param password the user password
     * @return the authenticated user
     */
    
    public User login(final String username, final String password) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}

