package com.eventapp.model.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.eventapp.model.enums.PaiementStatut;

class PaiementResponseDtoTest {

    @Test
    void defaultConstructorShouldCreateEmptyDto() {
        PaiementResponseDto dto = new PaiementResponseDto();

        assertThat(dto.getId()).isNull();
        assertThat(dto.getMontant()).isEqualTo(0.0);
        assertThat(dto.getStatut()).isNull();
        assertThat(dto.getDatePaiement()).isNull();
        assertThat(dto.getReservationId()).isNull();
    }

    @Test
    void settersShouldUpdateFields() {
        PaiementResponseDto dto = new PaiementResponseDto();

        dto.setId(1L);
        dto.setMontant(100.0);
        dto.setStatut(PaiementStatut.EFFECTUE);
        dto.setDatePaiement(LocalDate.of(2023, 10, 1));
        dto.setReservationId(2L);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getMontant()).isEqualTo(100.0);
        assertThat(dto.getStatut()).isEqualTo(PaiementStatut.EFFECTUE);
        assertThat(dto.getDatePaiement()).isEqualTo(LocalDate.of(2023, 10, 1));
        assertThat(dto.getReservationId()).isEqualTo(2L);
    }

    @Test
    void setIdShouldUpdateId() {
        PaiementResponseDto dto = new PaiementResponseDto();
        dto.setId(5L);

        assertThat(dto.getId()).isEqualTo(5L);
    }

    @Test
    void setMontantShouldUpdateMontant() {
        PaiementResponseDto dto = new PaiementResponseDto();
        dto.setMontant(250.5);

        assertThat(dto.getMontant()).isEqualTo(250.5);
    }

    @Test
    void setStatutShouldUpdateStatut() {
        PaiementResponseDto dto = new PaiementResponseDto();
        dto.setStatut(PaiementStatut.EN_ATTENTE);

        assertThat(dto.getStatut()).isEqualTo(PaiementStatut.EN_ATTENTE);
    }

    @Test
    void setDatePaiementShouldUpdateDatePaiement() {
        PaiementResponseDto dto = new PaiementResponseDto();
        LocalDate date = LocalDate.of(2023, 12, 25);
        dto.setDatePaiement(date);

        assertThat(dto.getDatePaiement()).isEqualTo(date);
    }

    @Test
    void setReservationIdShouldUpdateReservationId() {
        PaiementResponseDto dto = new PaiementResponseDto();
        dto.setReservationId(10L);

        assertThat(dto.getReservationId()).isEqualTo(10L);
    }
}