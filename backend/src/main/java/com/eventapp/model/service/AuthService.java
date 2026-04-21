package com.eventapp.model.service;

import org.springframework.stereotype.Service;

import com.eventapp.exception.AuthException.InvalidPasswordException;
import com.eventapp.exception.AuthException.UserAlreadyExistsException;
import com.eventapp.exception.AuthException.UserNotFoundException;
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
     * @param email email of the user 
     * @param password the user password
     * @return the saved user
     */
    public User register(final String username, final String email, 
        final  String password) {
        
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("Username déjà utilisé");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("Email déjà utilisé");
        }
        
        User user = new User(username, email, password, "USER");
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
            .orElseThrow(UserNotFoundException::new);
        
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }

        return user;
    }
}

