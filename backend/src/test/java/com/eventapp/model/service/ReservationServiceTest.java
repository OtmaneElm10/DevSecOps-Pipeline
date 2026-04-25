package com.eventapp.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.eventapp.exception.AuthException.UserNotFoundException;
import com.eventapp.exception.EventException.EventNotFoundException;
import com.eventapp.exception.ReservationException.InvalidReservationException;
import com.eventapp.exception.ReservationException.ReservationCapacityExceededException;
import com.eventapp.exception.ReservationException.ReservationNotFoundException;
import com.eventapp.model.entities.Event;
import com.eventapp.model.entities.Reservation;
import com.eventapp.model.entities.User;
import com.eventapp.model.enums.ReservationStatut;
import com.eventapp.repositories.EventRepository;
import com.eventapp.repositories.ReservationRepository;
import com.eventapp.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private PaiementService paiementService;

    @InjectMocks
    private ReservationService reservationService;

    private User buildUser(final Long id) {
        User user = new User();
        user.setId(id);
        user.setUsername("User" + id);
        return user;
    }

    private Event buildEvent(final Long id, final int capaciteMax) {
        Event event = new Event();
        event.setId(id);
        event.setCapaciteMax(capaciteMax);
        event.setPrix(10.0);
        event.setNbInscrits(0);
        return event;
    }

    private Reservation buildReservation(final User user,
            final Event event, final int nbPlaces) {
        Reservation r = new Reservation();
        r.setUser(user);
        r.setEvent(event);
        r.setNbPlaces(nbPlaces);
        return r;
    }

    @Test
    void getAllReservationsShouldReturnEmptyList() {
        when(reservationRepository.findAll()).thenReturn(List.of());

        assertThat(reservationService.getAllReservations()).isEmpty();
    }

    @Test
    void createReservationShouldThrowWhenNbPlacesIsZero() {
        Reservation r = buildReservation(buildUser(1L), buildEvent(1L, 10), 0);

        assertThatThrownBy(() -> reservationService.createReservation(r))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("Number of places must be greater than 0");
    }

    @Test
    void createReservationShouldThrowWhenNbPlacesIsNegative() {
        Reservation r = buildReservation(buildUser(1L), buildEvent(1L, 10), -1);

        assertThatThrownBy(() -> reservationService.createReservation(r))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("Number of places must be greater than 0");
    }

    @Test
    void createReservationShouldThrowWhenUserIsNull() {
        Reservation r = new Reservation();
        r.setNbPlaces(2);
        r.setUser(null);

        assertThatThrownBy(() -> reservationService.createReservation(r))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("User is required to make a reservation");
    }

    @Test
    void createReservationShouldThrowWhenUserIdIsNull() {
        User user = new User();
        user.setId(null);

        Reservation r = buildReservation(user, buildEvent(1L, 10), 2);

        assertThatThrownBy(() -> reservationService.createReservation(r))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("User is required to make a reservation");
    }

    @Test
    void createReservationShouldThrowWhenEventIsNull() {
        Reservation r = new Reservation();
        r.setNbPlaces(2);
        r.setUser(buildUser(1L));
        r.setEvent(null);

        assertThatThrownBy(() -> reservationService.createReservation(r))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("Event is required");
    }

    @Test
    void createReservationShouldThrowWhenEventIdIsNull() {
        Event event = new Event();
        event.setId(null);

        Reservation r = buildReservation(buildUser(1L), event, 2);

        assertThatThrownBy(() -> reservationService.createReservation(r))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("Event is required");
    }

    @Test
    void createReservationShouldThrowWhenUserNotFound() {
        Reservation r = buildReservation(buildUser(1L), buildEvent(1L, 10), 2);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservationService.createReservation(r))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void createReservationShouldThrowWhenEventNotFound() {
        Reservation r = buildReservation(buildUser(1L), buildEvent(1L, 10), 2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(buildUser(1L)));
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservationService.createReservation(r))
                .isInstanceOf(EventNotFoundException.class);
    }

    @Test
    void createReservationShouldThrowWhenCapacityExceeded() {
        User user = buildUser(1L);
        Event event = buildEvent(1L, 5);
        event.setNbInscrits(4);

        Reservation r = buildReservation(user, event, 3);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        assertThatThrownBy(() -> reservationService.createReservation(r))
                .isInstanceOf(ReservationCapacityExceededException.class);
    }

    @Test
    void createReservationShouldSaveAndReturn() {
        User user = buildUser(1L);
        Event event = buildEvent(1L, 10);
        event.setPrix(12.0);
        event.setNbInscrits(0);

        Reservation r = buildReservation(user, event, 2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(r);

        Reservation result = reservationService.createReservation(r);

        assertThat(result.getNbPlaces()).isEqualTo(2);
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getEvent()).isEqualTo(event);
        assertThat(result.getStatut()).isEqualTo(ReservationStatut.EN_ATTENTE_DE_PAIEMENT);
        assertThat(result.getMontantAttendu()).isEqualTo(24.0);
        assertThat(result.getDateCreation()).isNotNull();

        assertThat(event.getNbInscrits()).isEqualTo(2);

        verify(eventRepository).save(event);
        verify(reservationRepository).save(r);
        verify(paiementService).createPendingPaiement(r);
    }

    @Test
    void getReservationsByUserIdShouldThrowWhenUserNotFound() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> reservationService.getReservationsByUserId(99L))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void getReservationsByUserIdShouldReturnEmptyList() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(reservationRepository.findByUserId(1L)).thenReturn(List.of());

        assertThat(reservationService.getReservationsByUserId(1L)).isEmpty();
    }

    @Test
    void cancelReservationShouldThrowWhenNotFound() {
        when(reservationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservationService.cancelReservation(99L))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    void cancelReservationShouldThrowWhenAlreadyCancelled() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStatut(ReservationStatut.ANNULEE);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        assertThatThrownBy(() -> reservationService.cancelReservation(1L))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("Reservation is already cancelled");
    }

    @Test
    void cancelReservationShouldThrowWhenAlreadyPaid() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStatut(ReservationStatut.PAYEE);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        assertThatThrownBy(() -> reservationService.cancelReservation(1L))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("Cannot cancel a paid reservation");
    }

    @Test
    void cancelReservationShouldCancelReservationAndPayment() {
        Event event = new Event();
        event.setId(1L);
        event.setNbInscrits(5);

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setNbPlaces(2);
        reservation.setEvent(event);
        reservation.setStatut(ReservationStatut.EN_ATTENTE_DE_PAIEMENT);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        reservationService.cancelReservation(1L);

        assertThat(reservation.getStatut()).isEqualTo(ReservationStatut.ANNULEE);
        assertThat(event.getNbInscrits()).isEqualTo(3);

        verify(eventRepository).save(event);
        verify(paiementService).cancelPendingPaiement(1L);
        verify(reservationRepository).save(reservation);
    }
}
