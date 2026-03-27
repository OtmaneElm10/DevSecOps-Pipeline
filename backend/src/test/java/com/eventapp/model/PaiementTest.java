package com.eventapp.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.Paiement;
import com.eventapp.model.entities.Reservation;

class PaiementTest {

    @Test
    void constructorShouldSetAllFields() {
        Date date = new Date(System.currentTimeMillis());

        Paiement paiement = new Paiement(50.0f, "Payé", date);

        assertThat(paiement.getMontant()).isEqualTo(50.0f);
        assertThat(paiement.getStatut()).isEqualTo("Payé");
        assertThat(paiement.getDatePaiement()).isEqualTo(date);
    }

    @Test
    void defaultConstructorShouldCreateEmptyPaiement() {
        Paiement paiement = new Paiement();
        assertThat(paiement.getIdPaiement()).isNull();
        assertThat(paiement.getMontant()).isEqualTo(0.0f);
        assertThat(paiement.getStatut()).isNull();
        assertThat(paiement.getDatePaiement()).isNull();
        assertThat(paiement.getReservation()).isNull();
    }

    @Test
    void settersShouldUpdateFields() {
        Paiement paiement = new Paiement();
        Date date = new Date(System.currentTimeMillis());
        Reservation reservation = new Reservation();

        paiement.setIdPaiement(1L);
        paiement.setMontant(100.0f);
        paiement.setStatut("En attente");
        paiement.setDatePaiement(date);
        paiement.setReservation(reservation);

        assertThat(paiement.getIdPaiement()).isEqualTo(1L);
        assertThat(paiement.getMontant()).isEqualTo(100.0f);
        assertThat(paiement.getStatut()).isEqualTo("En attente");
        assertThat(paiement.getDatePaiement()).isEqualTo(date);
        assertThat(paiement.getReservation()).isEqualTo(reservation);
    }
}