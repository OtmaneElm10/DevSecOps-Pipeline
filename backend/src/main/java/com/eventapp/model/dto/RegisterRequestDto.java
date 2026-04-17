package com.eventapp.model.dto;

/**
 * Request body for user registration.
 */
public class RegisterRequestDto {

    private String username;
    private String email;
    private String password;

    /**
     * Default constructor.
     */
    public RegisterRequestDto() {
    }

    /**
     * Returns the username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the username.
     *
     * @param username username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Sets the email.
     *
     * @param email email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Sets the password.
     *
     * @param password password
     */
    public void setPassword(final String password) {
        this.password = password;
    }
}
