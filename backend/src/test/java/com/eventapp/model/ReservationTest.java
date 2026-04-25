package com.eventapp.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.Event;
import com.eventapp.model.entities.Reservation;
import com.eventapp.model.entities.User;
import com.eventapp.model.enums.ReservationStatut;

class ReservationTest {

    @Test
    void constructorShouldSetAllFields() {
        LocalDate date = LocalDate.now();

        Reservation reservation = new Reservation(5, ReservationStatut.PAYEE, date, 50.0);

        assertThat(reservation.getNbPlaces()).isEqualTo(5);
        assertThat(reservation.getStatut()).isEqualTo(ReservationStatut.PAYEE);
        assertThat(reservation.getDateCreation()).isEqualTo(date);
        assertThat(reservation.getMontantAttendu()).isEqualTo(50.0);
    }

    @Test
    void defaultConstructorShouldCreateEmptyReservation() {
        Reservation reservation = new Reservation();

        assertThat(reservation.getId()).isNull();
        assertThat(reservation.getNbPlaces()).isEqualTo(0);
        assertThat(reservation.getStatut()).isNull();
        assertThat(reservation.getDateCreation()).isNull();
        assertThat(reservation.getMontantAttendu()).isEqualTo(0.0);
        assertThat(reservation.getUser()).isNull();
        assertThat(reservation.getEvent()).isNull();
    }

    @Test
    void settersShouldUpdateFields() {
        Reservation reservation = new Reservation();
        LocalDate date = LocalDate.now();
        User user = new User();
        Event event = new Event();

        reservation.setId(1L);
        reservation.setNbPlaces(10);
        reservation.setStatut(ReservationStatut.ANNULEE);
        reservation.setDateCreation(date);
        reservation.setMontantAttendu(100.0);
        reservation.setUser(user);
        reservation.setEvent(event);

        assertThat(reservation.getId()).isEqualTo(1L);
        assertThat(reservation.getNbPlaces()).isEqualTo(10);
        assertThat(reservation.getStatut()).isEqualTo(ReservationStatut.ANNULEE);
        assertThat(reservation.getDateCreation()).isEqualTo(date);
        assertThat(reservation.getMontantAttendu()).isEqualTo(100.0);
        assertThat(reservation.getUser()).isEqualTo(user);
        assertThat(reservation.getEvent()).isEqualTo(event);
    }
}
