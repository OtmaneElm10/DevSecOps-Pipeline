package com.eventapp.exception;

/**
 * Exception thrown when a password is invalid.
 */
public class InvalidPasswordException extends RuntimeException {

    /**
     * Default constructor.
     */
    public InvalidPasswordException() {
        super("Invalid password");
    }
}
