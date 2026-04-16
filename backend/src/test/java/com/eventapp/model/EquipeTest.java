package com.eventapp.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.Equipe;

class EquipeTest {

    @Test
    void constructorShouldSetAllFields() {
        Equipe equipe = new Equipe("Equipe A");

        assertThat(equipe.getNomEquipe()).isEqualTo("Equipe A");
    }

    @Test
    void defaultConstructorShouldCreateEmptyEquipe() {
        Equipe equipe = new Equipe();
        assertThat(equipe.getId()).isNull();
        assertThat(equipe.getNomEquipe()).isNull();
    }

    @Test
    void settersShouldUpdateFields() {
        Equipe equipe = new Equipe();
        equipe.setId(1L);
        equipe.setNomEquipe("Equipe B");

        assertThat(equipe.getId()).isEqualTo(1L);
        assertThat(equipe.getNomEquipe()).isEqualTo("Equipe B");
    }
}
