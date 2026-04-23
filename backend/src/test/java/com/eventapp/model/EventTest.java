package com.eventapp.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.Event;
import com.eventapp.model.enums.EventType;

class EventTest {

    @Test
    void settersShouldSetAllFields() {
        LocalDate debut = LocalDate.of(2026, 4, 16);
        LocalDate fin = LocalDate.of(2026, 4, 17);

        Event event = new Event();
        event.setTitle("Tournoi");
        event.setDescription("Description");
        event.setLieu("Lyon");
        event.setDateDebut(debut);
        event.setDateFin(fin);
        event.setCapaciteMax(30);
        event.setPrix(10.0);
        event.setType(EventType.TOURNOI);

        assertThat(event.getTitle()).isEqualTo("Tournoi");
        assertThat(event.getDescription()).isEqualTo("Description");
        assertThat(event.getLieu()).isEqualTo("Lyon");
        assertThat(event.getDateDebut()).isEqualTo(debut);
        assertThat(event.getDateFin()).isEqualTo(fin);
        assertThat(event.getCapaciteMax()).isEqualTo(30);
        assertThat(event.getPrix()).isEqualTo(10.0);
        assertThat(event.getType()).isEqualTo(EventType.TOURNOI);
    }

    @Test
    void defaultConstructorShouldCreateEmptyEvent() {
        Event event = new Event();

        assertThat(event.getId()).isNull();
        assertThat(event.getTitle()).isNull();
        assertThat(event.getDescription()).isNull();
        assertThat(event.getLieu()).isNull();
        assertThat(event.getDateDebut()).isNull();
        assertThat(event.getDateFin()).isNull();
        assertThat(event.getType()).isNull();
    }
}
