package com.eventapp.model.dto;

/**
 * Request body for user login.
 */
public class LoginRequestDto {

    private String username;
    private String password;

    /**
     * Default constructor.
     */
    public LoginRequestDto() {
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
     * Sets the password.
     *
     * @param password password
     */
    public void setPassword(final String password) {
        this.password = password;
    }
}
