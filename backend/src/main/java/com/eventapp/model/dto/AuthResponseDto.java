package com.eventapp.model.dto;

/**
 * DTO returned after successful authentication.
 */
public class AuthResponseDto {

    private String token;
    private Long id;
    private String username;
    private String email;
    private String role;

    /**
     * Constructs an empty authentication response DTO.
     */
    public AuthResponseDto() {
    }

    /**
     * Constructs an authentication response DTO.
     * @param token the JWT token
     * @param id the user ID
     * @param username the username
     * @param email the email
     * @param role the user role
     */
    public AuthResponseDto(final String token, final Long id,
                           final String username, final String email,
                           final String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setRole(final String role) {
        this.role = role;
    }
}
