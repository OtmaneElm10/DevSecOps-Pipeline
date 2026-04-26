package com.eventapp.model.enums;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PaiementStatutTest {

    @Test
    void enumShouldContainAllValues() {
        assertThat(PaiementStatut.values()).hasSize(3);
    }

    @Test
    void enumValuesShouldBeCorrect() {
        assertThat(PaiementStatut.EN_ATTENTE).isNotNull();
        assertThat(PaiementStatut.EFFECTUE).isNotNull();
        assertThat(PaiementStatut.ANNULE).isNotNull();
    }

    @Test
    void valueOfShouldReturnCorrectEnum() {
        assertThat(PaiementStatut.valueOf("EN_ATTENTE")).isEqualTo(PaiementStatut.EN_ATTENTE);
        assertThat(PaiementStatut.valueOf("EFFECTUE")).isEqualTo(PaiementStatut.EFFECTUE);
        assertThat(PaiementStatut.valueOf("ANNULE")).isEqualTo(PaiementStatut.ANNULE);
    }
}