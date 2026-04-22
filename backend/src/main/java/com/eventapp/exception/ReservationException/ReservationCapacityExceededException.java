package com.eventapp.exception.ReservationException;



/**
 * Exception thrown when a reservation cannot be made because 
 * the event's capacity has been exceeded.
 */
public class ReservationCapacityExceededException extends RuntimeException {

    /**
     * Constructor with message.
     */
    public ReservationCapacityExceededException() {
        super("Not enough available places for this event");
    }
}
