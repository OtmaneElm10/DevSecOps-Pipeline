package com.eventapp.model.enums;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReservationStatutTest {

    @Test
    void enumShouldContainAllValues() {
        assertThat(ReservationStatut.values()).hasSize(3);
    }

    @Test
    void enumValuesShouldBeCorrect() {
        assertThat(ReservationStatut.EN_ATTENTE_DE_PAIEMENT).isNotNull();
        assertThat(ReservationStatut.PAYEE).isNotNull();
        assertThat(ReservationStatut.ANNULEE).isNotNull();
    }

    @Test
    void valueOfShouldReturnCorrectEnum() {
        assertThat(ReservationStatut.valueOf("EN_ATTENTE_DE_PAIEMENT"))
                .isEqualTo(ReservationStatut.EN_ATTENTE_DE_PAIEMENT);
        assertThat(ReservationStatut.valueOf("PAYEE"))
                .isEqualTo(ReservationStatut.PAYEE);
        assertThat(ReservationStatut.valueOf("ANNULEE"))
                .isEqualTo(ReservationStatut.ANNULEE);
    }
}