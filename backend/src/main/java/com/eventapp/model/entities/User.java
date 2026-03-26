package com.eventapp.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a user in the application.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String role;
    private String username; 

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructs a User.
     *
     * @param email the user email
     * @param password the user password
     * @param role the user role
     */
    public User(final String username, final String email,
         final String password, final String role) {
        
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
