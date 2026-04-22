package com.eventapp.exception.ReservationException;

/**
 * Exception thrown when a reservation is not found.
 */
public class ReservationNotFoundException extends RuntimeException {

    /**
     * Default constructor.
     */
    public ReservationNotFoundException() {
        super("Reservation not found");
    }
}
