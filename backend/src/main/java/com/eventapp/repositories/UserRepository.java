package com.eventapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventapp.model.entities.User;

/**
 * Repository for User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * find by email.
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);
}

