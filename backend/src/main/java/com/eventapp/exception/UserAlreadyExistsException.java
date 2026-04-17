package com.eventapp.exception;

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
