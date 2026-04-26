package com.eventapp.model.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PaiementCreateRequestDtoTest {

    @Test
    void defaultConstructorShouldCreateEmptyDto() {
        PaiementCreateRequestDto dto = new PaiementCreateRequestDto();

        assertThat(dto.getReservationId()).isNull();
    }

    @Test
    void setReservationIdShouldUpdateReservationId() {
        PaiementCreateRequestDto dto = new PaiementCreateRequestDto();
        dto.setReservationId(1L);

        assertThat(dto.getReservationId()).isEqualTo(1L);
    }
}