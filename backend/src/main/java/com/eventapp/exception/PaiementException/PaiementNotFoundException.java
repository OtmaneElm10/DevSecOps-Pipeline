package com.eventapp.exception.PaiementException;



/**
 * Exception thrown when a Paiement entity is not found in the database.
 */
public class PaiementNotFoundException extends RuntimeException {
    /**
     * Constructor for PaiementNotFoundException. Initializes the exception with a default message.
     */
    public PaiementNotFoundException() {
        super("Paiement not found");
    }
}
