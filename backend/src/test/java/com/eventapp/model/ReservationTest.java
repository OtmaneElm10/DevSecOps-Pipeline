package com.eventapp.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.Event;
import com.eventapp.model.entities.Reservation;
import com.eventapp.model.entities.User;

class ReservationTest {

    @Test
    void constructorShouldSetAllFields() {
        Date date = new Date(System.currentTimeMillis());

        Reservation reservation = new Reservation(5, "Confirmée", date, 50.0f);

        assertThat(reservation.getNbPlaces()).isEqualTo(5);
        assertThat(reservation.getStatut()).isEqualTo("Confirmée");
        assertThat(reservation.getDateCreation()).isEqualTo(date);
        assertThat(reservation.getMontantAttendu()).isEqualTo(50.0f);
    }

    @Test
    void defaultConstructorShouldCreateEmptyReservation() {
        Reservation reservation = new Reservation();
        assertThat(reservation.getIdReservation()).isNull();
        assertThat(reservation.getNbPlaces()).isEqualTo(0);
        assertThat(reservation.getStatut()).isNull();
        assertThat(reservation.getDateCreation()).isNull();
        assertThat(reservation.getMontantAttendu()).isEqualTo(0.0f);
        assertThat(reservation.getUtilisateur()).isNull();
        assertThat(reservation.getEvenement()).isNull();
    }

    @Test
    void settersShouldUpdateFields() {
        Reservation reservation = new Reservation();
        Date date = new Date(System.currentTimeMillis());
        User user = new User();
        Event event = new Event();

        reservation.setIdReservation(1L);
        reservation.setNbPlaces(10);
        reservation.setStatut("Annulée");
        reservation.setDateCreation(date);
        reservation.setMontantAttendu(100.0f);
        reservation.setUtilisateur(user);
        reservation.setEvenement(event);

        assertThat(reservation.getIdReservation()).isEqualTo(1L);
        assertThat(reservation.getNbPlaces()).isEqualTo(10);
        assertThat(reservation.getStatut()).isEqualTo("Annulée");
        assertThat(reservation.getDateCreation()).isEqualTo(date);
        assertThat(reservation.getMontantAttendu()).isEqualTo(100.0f);
        assertThat(reservation.getUtilisateur()).isEqualTo(user);
        assertThat(reservation.getEvenement()).isEqualTo(event);
    }
}