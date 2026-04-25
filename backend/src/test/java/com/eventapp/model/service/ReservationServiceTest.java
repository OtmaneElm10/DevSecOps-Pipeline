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
        Reservation r = buildReservation(user, event, 3);

        Reservation existing = new Reservation();
        existing.setNbPlaces(4);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(reservationRepository.findByEventId(1L)).thenReturn(List.of(existing));

        assertThatThrownBy(() -> reservationService.createReservation(r))
                .isInstanceOf(ReservationCapacityExceededException.class);
    }

    @Test
    void createReservationShouldSaveAndReturn() {
        User user = buildUser(1L);
        Event event = buildEvent(1L, 10);
        Reservation r = buildReservation(user, event, 2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(reservationRepository.findByEventId(1L)).thenReturn(List.of());
        when(reservationRepository.save(any(Reservation.class))).thenReturn(r);

        Reservation result = reservationService.createReservation(r);

        assertThat(result.getNbPlaces()).isEqualTo(2);
        verify(reservationRepository).save(r);
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
    void cancelReservationShouldDeleteWhenFound() {
        Event mockEvent = new Event();
        mockEvent.setId(1L);
        mockEvent.setNbInscrits(5);

        Reservation mockReservation = new Reservation();
        mockReservation.setId(1L);
        mockReservation.setNbPlaces(2);
        mockReservation.setEvent(mockEvent);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(mockReservation));

        reservationService.cancelReservation(1L);

        verify(eventRepository).save(mockEvent);
        verify(reservationRepository).delete(mockReservation);
    }
}
