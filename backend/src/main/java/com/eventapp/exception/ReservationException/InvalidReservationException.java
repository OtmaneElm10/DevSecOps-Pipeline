package com.eventapp.exception.ReservationException;

/**
 * Exception thrown when reservation data is invalid.
 */
public class InvalidReservationException extends RuntimeException {

    /**
     * Constructor with message.
     *
     * @param message error message
     */
    public InvalidReservationException(final String message) {
        super(message);
    }
}
