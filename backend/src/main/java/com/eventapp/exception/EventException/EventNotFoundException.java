package com.eventapp.exception.EventException;

/**
 * Exception thrown when an event is not found.
 */
public class EventNotFoundException extends RuntimeException {

    /**
     * Default constructor.
     */
    public EventNotFoundException() {
        super("Event not found");
    }
}
