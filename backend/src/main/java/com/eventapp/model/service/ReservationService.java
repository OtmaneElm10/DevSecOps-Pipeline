package com.eventapp.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventapp.model.entities.Reservation;

import com.eventapp.repositories.ReservationRepository;
import com.eventapp.repositories.UserRepository;
import com.eventapp.repositories.EventRepository;

import com.eventapp.exception.ReservationException.InvalidReservationException;



/**
 * Reservation service.
 */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    /**
     * Constructor.
     * @param reservationRepository reservation repository
     */
    public ReservationService(final ReservationRepository reservationRepository,
        final UserRepository userRepository, final EventRepository eventRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Creates a new reservation.
     * @param reservation reservation to create
     * @return
     */
    public Reservation createReservation(final Reservation reservation) {
        if (reservation.getNbPlaces() <= 0) {
            throw new InvalidReservationException("Number of places must be greater than 0");
        }

        return reservationRepository.save(reservation);
    }

    /**
     * Returns reservations by user ID.
     * @param userId user ID
     * @return list of reservations for the specified user ID
     */
    public List<Reservation> getReservationsByUserId(final Long userId) {
        return null; // TODO: implement this method to return reservations by user ID
    }

    
    /**
     * Cancels a reservation by its ID.
     * @param reservationId reservation ID to cancel
     */
    public void cancelReservation(final Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
