package com.eventapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventapp.model.entities.User;

/**
 * Repository for User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * find by username.
     * @param username username of the user 
     */
    Optional<User> findByUsername(String username);

    
    /**
     * find by email.
     * @param email email of the user
     * @return
     */
    Optional<User> findByEmail(String email);

}

