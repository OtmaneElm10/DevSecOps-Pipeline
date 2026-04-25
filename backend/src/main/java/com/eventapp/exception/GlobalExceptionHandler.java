package com.eventapp.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eventapp.exception.AuthException.InvalidPasswordException;
import com.eventapp.exception.AuthException.UserAlreadyExistsException;
import com.eventapp.exception.AuthException.UserNotFoundException;
import com.eventapp.exception.EventException.EventNotFoundException;
import com.eventapp.exception.PaiementException.InvalidPaymentException;
import com.eventapp.exception.PaiementException.PaiementNotFoundException;
import com.eventapp.exception.ReservationException.InvalidReservationException;
import com.eventapp.exception.ReservationException.ReservationCapacityExceededException;
import com.eventapp.exception.ReservationException.ReservationNotFoundException;

/**
 * Global exception handler for the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles user not found exceptions.
     *
     * @param e exception
     * @return HTTP 404 response
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(
            final UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", e.getMessage()));
    }

    /**
     * Handles event not found exceptions.
     * @param e exception
     * @return HTTP 404 response
     */
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEventNotFound(
        final EventNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", e.getMessage()));       
    }

    /**
     * Handles invalid password exceptions.
     *
     * @param e exception
     * @return HTTP 401 response
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPassword(
            final InvalidPasswordException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", e.getMessage()));
    }

    /**
     * Handles user already exists exceptions.
     *
     * @param e exception
     * @return HTTP 409 response
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExists(
            final UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message", e.getMessage()));
    }

    /**
     * Handles generic runtime exceptions.
     *
     * @param e exception
     * @return HTTP 400 response
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(
            final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", e.getMessage()));
    }

    /**
     * Handles reservation capacity exceeded exceptions.
     * @param e exception
     * @return HTTP 409 response
     */
    @ExceptionHandler(ReservationCapacityExceededException.class)
    public ResponseEntity<Map<String, String>> handleCapacityExceeded(
                final ReservationCapacityExceededException e) {       
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message", e.getMessage()));
    }

    /**
     * Handles invalid reservation exceptions.
     * @param e exception
     * @return HTTP 400 response
     */
    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<Map<String, String>> handleInvalidReservation(
            final InvalidReservationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", e.getMessage()));
    }

    /**
     * Handles reservation not found exceptions.
     * @param e exception
     * @return HTTP 404 response
     */
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleReservationNotFound(
            final ReservationNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", e.getMessage()));
    }

    /**
     * Handles paiement not found exceptions.
     * @param e exception
     * @return HTTP 404 response
     */
    @ExceptionHandler(PaiementNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePaiementNotFound(
                final PaiementNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", e.getMessage()));
    }

    /**
     * Handles invalid payment exceptions.
     * @param e exception
     * @return HTTP 400 response
     */
    @ExceptionHandler(InvalidPaymentException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPayment(
            final InvalidPaymentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", e.getMessage()));
    }


}
