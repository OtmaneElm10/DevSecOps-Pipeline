package com.eventapp.model.service;

import com.eventapp.exception.AuthException.UserNotFoundException;
import com.eventapp.model.entities.User;
import com.eventapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;


    /**
     *
     * @param userRepository the user repository
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new); 
    }
}