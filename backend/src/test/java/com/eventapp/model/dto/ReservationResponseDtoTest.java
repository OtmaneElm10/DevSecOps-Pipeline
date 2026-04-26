package com.eventapp.model.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.eventapp.model.enums.ReservationStatut;

class ReservationResponseDtoTest {

    @Test
    void defaultConstructorShouldCreateEmptyDto() {
        ReservationResponseDto dto = new ReservationResponseDto();

        assertThat(dto.getId()).isNull();
        assertThat(dto.getNbPlaces()).isEqualTo(0);
        assertThat(dto.getStatut()).isNull();
        assertThat(dto.getDateCreation()).isNull();
        assertThat(dto.getMontantAttendu()).isEqualTo(0.0);
        assertThat(dto.getUserId()).isNull();
        assertThat(dto.getUsername()).isNull();
        assertThat(dto.getEventId()).isNull();
        assertThat(dto.getEventTitle()).isNull();
    }

    @Test
    void settersShouldUpdateFields() {
        ReservationResponseDto dto = new ReservationResponseDto();

        dto.setId(1L);
        dto.setNbPlaces(5);
        dto.setStatut(ReservationStatut.PAYEE);
        dto.setDateCreation(LocalDate.of(2023, 9, 15));
        dto.setMontantAttendu(150.0);
        dto.setUserId(2L);
        dto.setUsername("john_doe");
        dto.setEventId(3L);
        dto.setEventTitle("Concert Rock");

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getNbPlaces()).isEqualTo(5);
        assertThat(dto.getStatut()).isEqualTo(ReservationStatut.PAYEE);
        assertThat(dto.getDateCreation()).isEqualTo(LocalDate.of(2023, 9, 15));
        assertThat(dto.getMontantAttendu()).isEqualTo(150.0);
        assertThat(dto.getUserId()).isEqualTo(2L);
        assertThat(dto.getUsername()).isEqualTo("john_doe");
        assertThat(dto.getEventId()).isEqualTo(3L);
        assertThat(dto.getEventTitle()).isEqualTo("Concert Rock");
    }

    @Test
    void setIdShouldUpdateId() {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setId(10L);

        assertThat(dto.getId()).isEqualTo(10L);
    }

    @Test
    void setNbPlacesShouldUpdateNbPlaces() {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setNbPlaces(3);

        assertThat(dto.getNbPlaces()).isEqualTo(3);
    }

    @Test
    void setStatutShouldUpdateStatut() {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setStatut(ReservationStatut.ANNULEE);

        assertThat(dto.getStatut()).isEqualTo(ReservationStatut.ANNULEE);
    }

    @Test
    void setDateCreationShouldUpdateDateCreation() {
        ReservationResponseDto dto = new ReservationResponseDto();
        LocalDate date = LocalDate.of(2023, 11, 20);
        dto.setDateCreation(date);

        assertThat(dto.getDateCreation()).isEqualTo(date);
    }

    @Test
    void setMontantAttenduShouldUpdateMontantAttendu() {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setMontantAttendu(200.75);

        assertThat(dto.getMontantAttendu()).isEqualTo(200.75);
    }

    @Test
    void setUserIdShouldUpdateUserId() {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setUserId(7L);

        assertThat(dto.getUserId()).isEqualTo(7L);
    }

    @Test
    void setUsernameShouldUpdateUsername() {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setUsername("alice_smith");

        assertThat(dto.getUsername()).isEqualTo("alice_smith");
    }

    @Test
    void setEventIdShouldUpdateEventId() {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setEventId(4L);

        assertThat(dto.getEventId()).isEqualTo(4L);
    }

    @Test
    void setEventTitleShouldUpdateEventTitle() {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setEventTitle("Festival Jazz");

        assertThat(dto.getEventTitle()).isEqualTo("Festival Jazz");
    }
}