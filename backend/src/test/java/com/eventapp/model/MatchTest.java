package com.eventapp.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.Equipe;
import com.eventapp.model.entities.Event;
import com.eventapp.model.entities.Match;

class MatchTest {

    @Test
    void constructorShouldSetAllFields() {
        LocalDate date = LocalDate.now();
        Equipe equipeA = new Equipe("Equipe A");
        Equipe equipeB = new Equipe("Equipe B");
        Event event = new Event();

        Match match = new Match(date, 2, 1, "Terminé", equipeA, equipeB, event);

        assertThat(match.getDateMatch()).isEqualTo(date);
        assertThat(match.getScoreA()).isEqualTo(2);
        assertThat(match.getScoreB()).isEqualTo(1);
        assertThat(match.getStatut()).isEqualTo("Terminé");
        assertThat(match.getEquipeA()).isEqualTo(equipeA);
        assertThat(match.getEquipeB()).isEqualTo(equipeB);
        assertThat(match.getEvent()).isEqualTo(event);
    }

    @Test
    void defaultConstructorShouldCreateEmptyMatch() {
        Match match = new Match();

        assertThat(match.getId()).isNull();
        assertThat(match.getDateMatch()).isNull();
        assertThat(match.getScoreA()).isNull(); // important : Integer
        assertThat(match.getScoreB()).isNull();
        assertThat(match.getStatut()).isNull();
        assertThat(match.getEquipeA()).isNull();
        assertThat(match.getEquipeB()).isNull();
        assertThat(match.getEvent()).isNull();
    }

    @Test
    void settersShouldUpdateFields() {
        Match match = new Match();
        LocalDate date = LocalDate.now();
        Equipe equipeA = new Equipe("Equipe A");
        Event event = new Event();

        match.setId(1L);
        match.setDateMatch(date);
        match.setScoreA(3);
        match.setScoreB(0);
        match.setStatut("En cours");
        match.setEquipeA(equipeA);
        match.setEquipeB(null);
        match.setEvent(event);

        assertThat(match.getId()).isEqualTo(1L);
        assertThat(match.getDateMatch()).isEqualTo(date);
        assertThat(match.getScoreA()).isEqualTo(3);
        assertThat(match.getScoreB()).isEqualTo(0);
        assertThat(match.getStatut()).isEqualTo("En cours");
        assertThat(match.getEquipeA()).isEqualTo(equipeA);
        assertThat(match.getEquipeB()).isNull();
        assertThat(match.getEvent()).isEqualTo(event);
    }
}
