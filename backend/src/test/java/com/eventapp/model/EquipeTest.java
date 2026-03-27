package com.eventapp.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.Equipe;

class EquipeTest {

    @Test
    void constructorShouldSetAllFields() {
        Equipe equipe = new Equipe("Equipe A", "0123456789");

        assertThat(equipe.getNomEquipe()).isEqualTo("Equipe A");
        assertThat(equipe.getTelephone()).isEqualTo("0123456789");
    }

    @Test
    void defaultConstructorShouldCreateEmptyEquipe() {
        Equipe equipe = new Equipe();
        assertThat(equipe.getId()).isNull();
        assertThat(equipe.getNomEquipe()).isNull();
        assertThat(equipe.getTelephone()).isNull();
    }

    @Test
    void settersShouldUpdateFields() {
        Equipe equipe = new Equipe();
        equipe.setId(1L);
        equipe.setNomEquipe("Equipe B");
        equipe.setTelephone("0987654321");

        assertThat(equipe.getId()).isEqualTo(1L);
        assertThat(equipe.getNomEquipe()).isEqualTo("Equipe B");
        assertThat(equipe.getTelephone()).isEqualTo("0987654321");
    }
}