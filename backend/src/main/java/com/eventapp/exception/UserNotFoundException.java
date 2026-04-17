package com.eventapp.exception;

/**
 * Exception thrown when a user is not found.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Default constructor.
     */
    public UserNotFoundException() {
        super("User not found");
    }
}
