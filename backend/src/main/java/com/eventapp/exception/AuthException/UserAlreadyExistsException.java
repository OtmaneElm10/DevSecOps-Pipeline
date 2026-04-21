package com.eventapp.exception.AuthException;

/**
 * Exception thrown when a user already exists.
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param message exception message
     */
    public UserAlreadyExistsException(final String message) {
        super(message);
    }
}
