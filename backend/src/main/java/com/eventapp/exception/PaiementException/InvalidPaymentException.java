package com.eventapp.exception.PaiementException;

/**
 * Exception thrown when payment data is invalid.
 */
public class InvalidPaymentException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param message exception message
     */
    public InvalidPaymentException(final String message) {
        super(message);
    }
}
