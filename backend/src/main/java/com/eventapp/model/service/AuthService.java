package com.eventapp.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs the authentication service.
     *
     * @param userRepository the user repository
     * @param passwordEncoder the password encoder
     */
    public AuthService(final UserRepository userRepository,
        final PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, encodedPassword, "USER");
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
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }

        return user;
    }
}

