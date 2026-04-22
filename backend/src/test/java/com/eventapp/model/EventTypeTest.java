package com.eventapp.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.EventType;

class EventTypeTest {

    @Test
    void enumShouldContainAllValues() {
        EventType[] values = EventType.values();
        assertThat(values).hasSize(4);
    }

    @Test
    void enumValuesShouldBeCorrect() {
        assertThat(EventType.MATCH).isNotNull();
        assertThat(EventType.TOURNOI).isNotNull();
        assertThat(EventType.STAGE).isNotNull();
        assertThat(EventType.SOIREE).isNotNull();
    }

    @Test
    void valueOfShouldReturnCorrectEnum() {
        assertThat(EventType.valueOf("MATCH")).isEqualTo(EventType.MATCH);
        assertThat(EventType.valueOf("TOURNOI")).isEqualTo(EventType.TOURNOI);
        assertThat(EventType.valueOf("STAGE")).isEqualTo(EventType.STAGE);
        assertThat(EventType.valueOf("SOIREE")).isEqualTo(EventType.SOIREE);
    }
}