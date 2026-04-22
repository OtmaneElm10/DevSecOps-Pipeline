package com.eventapp.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventapp.exception.AuthException.UserNotFoundException;
import com.eventapp.exception.EventException.EventNotFoundException;
import com.eventapp.exception.ReservationException.InvalidReservationException;
import com.eventapp.exception.ReservationException.ReservationNotFoundException;
import com.eventapp.exception.ReservationException.ReservationCapacityExceededException;
import com.eventapp.model.entities.Event;
import com.eventapp.model.entities.Reservation;
import com.eventapp.model.entities.User;
import com.eventapp.repositories.EventRepository;
import com.eventapp.repositories.ReservationRepository;
import com.eventapp.repositories.UserRepository;



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
     * @return the created reservation
     * @throws InvalidReservationException if the reservation is invalid 
     * (e.g., number of places is not positive, user or event is missing)
     * @throws UserNotFoundException if the user associated with the reservation does not exist
     * @throws EventNotFoundException if the event associated with the reservation does not exist
     */
    public Reservation createReservation(final Reservation reservation) {
        
        if (reservation.getNbPlaces() <= 0) {
            throw new InvalidReservationException("Number of places must be greater than 0");
        }

        if (reservation.getUser() == null || reservation.getUser().getId() == null) {
            throw new InvalidReservationException("User is required to make a reservation");
        }

        if (reservation.getEvent() == null 
            || reservation.getEvent().getId() == null) {

            throw new InvalidReservationException("Event is required");
        }

        User user = userRepository.findById(reservation.getUser().getId())
            .orElseThrow(UserNotFoundException::new);

        Event event = eventRepository.findById(reservation.getEvent().getId())
            .orElseThrow(EventNotFoundException::new);

        int reservedPlaces = reservationRepository.findByEventId(event.getId())
            .stream()
            .mapToInt(Reservation::getNbPlaces)
            .sum();

        if (reservedPlaces + reservation.getNbPlaces() > event.getCapaciteMax()) {
            throw new ReservationCapacityExceededException();
        }

        reservation.setUser(user);
        reservation.setEvent(event);
        return reservationRepository.save(reservation);
    }

    /**
     * Returns reservations by user ID.
     * @param userId user ID
     * @return list of reservations for the specified user ID
     */
    public List<Reservation> getReservationsByUserId(final Long userId) {
        
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        
        return reservationRepository.findByUserId(userId);
    }

    
    /**
     * Cancels a reservation by its ID.
     * @param reservationId reservation ID to cancel
     */
    public void cancelReservation(final Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new ReservationNotFoundException();
        }

        reservationRepository.deleteById(reservationId);
    }
}

