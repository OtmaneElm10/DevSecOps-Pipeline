package com.eventapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.eventapp.model.dto.ReservationResponseDto;
import com.eventapp.model.entities.Event;
import com.eventapp.model.entities.Reservation;
import com.eventapp.model.entities.User;
import com.eventapp.model.enums.ReservationStatut;
import com.eventapp.model.service.ReservationService;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        reservationController = new ReservationController(reservationService);
    }

    private ReservationResponseDto buildDto(final Long id, final Long userId,
            final Long eventId) {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setId(id);
        dto.setUserId(userId);
        dto.setEventId(eventId);
        dto.setNbPlaces(2);
        dto.setStatut(ReservationStatut.EN_ATTENTE_DE_PAIEMENT);
        return dto;
    }

    private Reservation buildReservation(final Long userId, final Long eventId) {
        User user = new User();
        user.setId(userId);
        Event event = new Event();
        event.setId(eventId);
        Reservation r = new Reservation();
        r.setUser(user);
        r.setEvent(event);
        r.setNbPlaces(2);
        return r;
    }

    @Test
    void getAllReservationsShouldReturn200() {
        when(reservationService.getAllReservations()).thenReturn(List.of());

        ResponseEntity<List<ReservationResponseDto>> response =
                reservationController.getAllReservations();

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void getAllReservationsShouldReturnList() {
        List<ReservationResponseDto> dtos = List.of(
                buildDto(1L, 1L, 1L),
                buildDto(2L, 2L, 1L));
        when(reservationService.getAllReservations()).thenReturn(dtos);

        ResponseEntity<List<ReservationResponseDto>> response =
                reservationController.getAllReservations();

        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getId()).isEqualTo(1L);
    }

    @Test
    void getAllReservationsShouldReturnEmptyList() {
        when(reservationService.getAllReservations()).thenReturn(List.of());

        ResponseEntity<List<ReservationResponseDto>> response =
                reservationController.getAllReservations();

        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void getReservationsByIdShouldReturn200() {
        when(reservationService.getReservationsByUserId(1L))
                .thenReturn(List.of(buildDto(1L, 1L, 1L)));

        ResponseEntity<List<ReservationResponseDto>> response =
                reservationController.getReservationsById(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void getReservationsByIdShouldReturnUserReservations() {
        List<ReservationResponseDto> dtos = List.of(buildDto(1L, 1L, 1L));
        when(reservationService.getReservationsByUserId(1L)).thenReturn(dtos);

        ResponseEntity<List<ReservationResponseDto>> response =
                reservationController.getReservationsById(1L);

        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getUserId()).isEqualTo(1L);
        verify(reservationService).getReservationsByUserId(1L);
    }

    @Test
    void createReservationShouldReturn201() {
        Reservation reservation = buildReservation(1L, 1L);
        when(reservationService.createReservation(reservation)).thenReturn(reservation);

        ResponseEntity<Reservation> response =
                reservationController.createReservation(reservation);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void createReservationShouldReturnCreatedReservation() {
        Reservation reservation = buildReservation(1L, 1L);
        when(reservationService.createReservation(reservation)).thenReturn(reservation);

        ResponseEntity<Reservation> response =
                reservationController.createReservation(reservation);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNbPlaces()).isEqualTo(2);
        verify(reservationService).createReservation(reservation);
    }

    @Test
    void cancelReservationShouldReturn204() {
        ResponseEntity<Void> response = reservationController.cancelReservation(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(reservationService).cancelReservation(1L);
    }
}