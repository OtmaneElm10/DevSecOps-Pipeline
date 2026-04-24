package com.eventapp.model.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eventapp.exception.AuthException.UserNotFoundException;
import com.eventapp.exception.EventException.EventNotFoundException;
import com.eventapp.exception.ReservationException.InvalidReservationException;
import com.eventapp.exception.ReservationException.ReservationCapacityExceededException;
import com.eventapp.exception.ReservationException.ReservationNotFoundException;
import com.eventapp.model.dto.ReservationResponseDto;
import com.eventapp.model.entities.Event;
import com.eventapp.model.entities.Reservation;
import com.eventapp.model.entities.User;
import com.eventapp.model.enums.ReservationStatut;
import com.eventapp.repositories.EventRepository;
import com.eventapp.repositories.ReservationRepository;
import com.eventapp.repositories.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Reservation service.
 */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final PaiementService paiementService;

    /**
     * Constructor.
     *
     * @param reservationRepository reservation repository
     * @param userRepository user repository
     * @param eventRepository event repository
     * @param paiementService payment service
     */
    public ReservationService(
            final ReservationRepository reservationRepository,
            final UserRepository userRepository,
            final EventRepository eventRepository,
            final PaiementService paiementService) {
        
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.paiementService = paiementService;
    }

    /**
     * Returns all reservations.
     *
     * @return list of reservation response DTOs
     */
    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Creates a new reservation.
     *
     * @param reservation reservation to create
     * @return the created reservation
     * @throws InvalidReservationException if the reservation is invalid
     * @throws UserNotFoundException if the user does not exist
     * @throws EventNotFoundException if the event does not exist
     * @throws ReservationCapacityExceededException if event capacity is exceeded
     */
    @Transactional
    public Reservation createReservation(final Reservation reservation) {

        if (reservation.getNbPlaces() <= 0) {
            throw new InvalidReservationException(
                    "Number of places must be greater than 0");
        }

        if (reservation.getUser() == null || reservation.getUser().getId() == null) {
            throw new InvalidReservationException(
                    "User is required to make a reservation");
        }

        if (reservation.getEvent() == null || reservation.getEvent().getId() == null) {
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
        reservation.setDateCreation(LocalDate.now());
        reservation.setStatut(ReservationStatut.EN_ATTENTE_DE_PAIEMENT);
        reservation.setMontantAttendu(event.getPrix() * reservation.getNbPlaces());
        
        Reservation savedReservation = reservationRepository.save(reservation);

        paiementService.createPendingPaiement(savedReservation);

        return savedReservation;
    }

    /**
     * Returns reservations by user ID.
     *
     * @param userId user ID
     * @return list of reservations for the specified user ID
     */
    public List<ReservationResponseDto> getReservationsByUserId(final Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }

        return reservationRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Cancels a reservation by its ID.
     *
     * @param reservationId reservation ID to cancel
     */
    @Transactional
    public void cancelReservation(final Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);

        if (ReservationStatut.ANNULEE.equals(reservation.getStatut())) {
            throw new InvalidReservationException("Reservation is already cancelled");
        }

        if (ReservationStatut.PAYEE.equals(reservation.getStatut())) {
            throw new InvalidReservationException("Cannot cancel a paid reservation");
        }

        paiementService.cancelPendingPaiement(reservationId);
        reservation.setStatut(ReservationStatut.ANNULEE);
    }

    /**
     * Converts a reservation entity to a response DTO.
     *
     * @param reservation reservation entity
     * @return reservation response DTO
     */
    private ReservationResponseDto toDto(final Reservation reservation) {
        ReservationResponseDto dto = new ReservationResponseDto();

        dto.setId(reservation.getId());
        dto.setNbPlaces(reservation.getNbPlaces());
        dto.setStatut(reservation.getStatut());
        dto.setDateCreation(reservation.getDateCreation());
        dto.setMontantAttendu(reservation.getMontantAttendu());

        dto.setUserId(reservation.getUser().getId());
        dto.setUsername(reservation.getUser().getUsername());

        dto.setEventId(reservation.getEvent().getId());
        dto.setEventTitle(reservation.getEvent().getTitle());

        return dto;
    }
}
