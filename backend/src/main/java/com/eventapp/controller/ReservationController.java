package com.eventapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventapp.model.service.ReservationService;
import com.eventapp.model.dto.ReservationResponseDto;
import com.eventapp.model.entities.Reservation;

/**
 * Reservation controller api.
 */
@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * Constructor.
     * @param reservationService reservation service
     */
    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    /**
     * Get all reservations.
     * @return list of all reservations
     */
    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }


    /**
     * Get reservations by user ID.
     * @param userId user ID
     * @return list of reservations for the specified user ID
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationResponseDto>> getReservationsById(
        final @PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
    }

    /**
     * Creates a new reservation.
     * @param reservation reservation to create
     * @return the created reservation
     */
    @PostMapping
    public ResponseEntity<Reservation> createReservation(final @RequestBody 
        Reservation reservation) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(reservationService.createReservation(reservation));
    }

    /**
    * Cancels a reservation by ID.
    * @param reservationId reservation ID to cancel
    */
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(final @PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

}
