package com.eventapp.model;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.Event;

import java.sql.Date;
import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    void constructorShouldSetAllFields() {
        Date debut = new Date(1000L);
        Date fin   = new Date(2000L);

        Event event = new Event("Tournoi", "Description", "Lyon", debut, fin, 30, 10.0f);

        assertThat(event.getTitle()).isEqualTo("Tournoi");
        assertThat(event.getDescription()).isEqualTo("Description");
        assertThat(event.getLieu()).isEqualTo("Lyon");
        assertThat(event.getDateDebut()).isEqualTo(debut);
        assertThat(event.getDateFin()).isEqualTo(fin);
        assertThat(event.getCapaciteMax()).isEqualTo(30);
        assertThat(event.getPrix()).isEqualTo(10.0f);
    }

    @Test
    void defaultConstructorShouldCreateEmptyEvent() {
        Event event = new Event();
        assertThat(event.getId()).isNull();
        assertThat(event.getTitle()).isNull();
    }
}
