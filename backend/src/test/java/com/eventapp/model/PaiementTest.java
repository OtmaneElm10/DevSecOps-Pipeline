package com.eventapp.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.Paiement;
import com.eventapp.model.entities.Reservation;
import com.eventapp.model.enums.PaiementStatut;

class PaiementTest {

    @Test
    void constructorShouldSetAllFields() {
        LocalDate date = LocalDate.now();

        Paiement paiement = new Paiement(50.0, PaiementStatut.EFFECTUE, date, new Reservation());

        assertThat(paiement.getMontant()).isEqualTo(50.0);
        assertThat(paiement.getStatut()).isEqualTo(PaiementStatut.EFFECTUE);
        assertThat(paiement.getDatePaiement()).isEqualTo(date);
        assertThat(paiement.getReservation()).isNotNull();

    }

    @Test
    void defaultConstructorShouldCreateEmptyPaiement() {
        Paiement paiement = new Paiement();

        assertThat(paiement.getId()).isNull();
        assertThat(paiement.getMontant()).isEqualTo(0.0);
        assertThat(paiement.getStatut()).isNull();
        assertThat(paiement.getDatePaiement()).isNull();
        assertThat(paiement.getReservation()).isNull();
    }

    @Test
    void settersShouldUpdateFields() {
        Paiement paiement = new Paiement();
        LocalDate date = LocalDate.now();
        Reservation reservation = new Reservation();

        paiement.setId(1L);
        paiement.setMontant(100.0);
        paiement.setStatut(PaiementStatut.EN_ATTENTE);
        paiement.setDatePaiement(date);
        paiement.setReservation(reservation);

        assertThat(paiement.getId()).isEqualTo(1L);
        assertThat(paiement.getMontant()).isEqualTo(100.0);
        assertThat(paiement.getStatut()).isEqualTo(PaiementStatut.EN_ATTENTE);
        assertThat(paiement.getDatePaiement()).isEqualTo(date);
        assertThat(paiement.getReservation()).isEqualTo(reservation);
    }
}
