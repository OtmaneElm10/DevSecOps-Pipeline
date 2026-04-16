package com.eventapp.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.Paiement;
import com.eventapp.model.entities.Reservation;

class PaiementTest {

    @Test
    void constructorShouldSetAllFields() {
        LocalDate date = LocalDate.now();

        Paiement paiement = new Paiement(50.0, "Payé", date);

        assertThat(paiement.getMontant()).isEqualTo(50.0);
        assertThat(paiement.getStatut()).isEqualTo("Payé");
        assertThat(paiement.getDatePaiement()).isEqualTo(date);
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
        paiement.setStatut("En attente");
        paiement.setDatePaiement(date);
        paiement.setReservation(reservation);

        assertThat(paiement.getId()).isEqualTo(1L);
        assertThat(paiement.getMontant()).isEqualTo(100.0);
        assertThat(paiement.getStatut()).isEqualTo("En attente");
        assertThat(paiement.getDatePaiement()).isEqualTo(date);
        assertThat(paiement.getReservation()).isEqualTo(reservation);
    }
}
