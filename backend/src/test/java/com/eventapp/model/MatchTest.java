package com.eventapp.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.Equipe;
import com.eventapp.model.entities.Event;
import com.eventapp.model.entities.Match;

class MatchTest {

    @Test
    void constructorShouldSetAllFields() {
        Date date = new Date(System.currentTimeMillis());
        Equipe equipeA = new Equipe("Equipe A", "0123456789");
        Equipe equipeB = new Equipe("Equipe B", "0987654321");
        Event event = new Event("Match Event", "Description", "Stade", date, date, 100, 10.0f);

        Match match = new Match(date, 2, 1, "Terminé", equipeA, equipeB, event);

        assertThat(match.getDateMatch()).isEqualTo(date);
        assertThat(match.getScoreA()).isEqualTo(2);
        assertThat(match.getScoreB()).isEqualTo(1);
        assertThat(match.getStatut()).isEqualTo("Terminé");
        assertThat(match.getEquipeA()).isEqualTo(equipeA);
        assertThat(match.getEquipeB()).isEqualTo(equipeB);
        assertThat(match.getEvenement()).isEqualTo(event);
    }

    @Test
    void defaultConstructorShouldCreateEmptyMatch() {
        Match match = new Match();
        assertThat(match.getId()).isNull();
        assertThat(match.getDateMatch()).isNull();
        assertThat(match.getScoreA()).isEqualTo(0);
        assertThat(match.getScoreB()).isEqualTo(0);
        assertThat(match.getStatut()).isNull();
        assertThat(match.getEquipeA()).isNull();
        assertThat(match.getEquipeB()).isNull();
        assertThat(match.getEvenement()).isNull();
    }

    @Test
    void settersShouldUpdateFields() {
        Match match = new Match();
        Date date = new Date(System.currentTimeMillis());
        Equipe equipeA = new Equipe("Equipe A", "0123456789");
        Event event = new Event();

        match.setId(1L);
        match.setDateMatch(date);
        match.setScoreA(3);
        match.setScoreB(0);
        match.setStatut("En cours");
        match.setEquipeA(equipeA);
        match.setEquipeB(null);
        match.setEvenement(event);

        assertThat(match.getId()).isEqualTo(1L);
        assertThat(match.getDateMatch()).isEqualTo(date);
        assertThat(match.getScoreA()).isEqualTo(3);
        assertThat(match.getScoreB()).isEqualTo(0);
        assertThat(match.getStatut()).isEqualTo("En cours");
        assertThat(match.getEquipeA()).isEqualTo(equipeA);
        assertThat(match.getEquipeB()).isNull();
        assertThat(match.getEvenement()).isEqualTo(event);
    }
}