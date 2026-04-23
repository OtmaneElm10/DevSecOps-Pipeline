package com.eventapp.model.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LoginRequestDtoTest {

    @Test
    void defaultConstructorShouldCreateEmptyDto() {
        LoginRequestDto dto = new LoginRequestDto();

        assertThat(dto.getUsername()).isNull();
        assertThat(dto.getPassword()).isNull();
    }

    @Test
    void settersShouldUpdateFields() {
        LoginRequestDto dto = new LoginRequestDto();

        dto.setUsername("john");
        dto.setPassword("pass123");

        assertThat(dto.getUsername()).isEqualTo("john");
        assertThat(dto.getPassword()).isEqualTo("pass123");
    }

    @Test
    void setUsernameShouldUpdateUsername() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setUsername("alice");

        assertThat(dto.getUsername()).isEqualTo("alice");
    }

    @Test
    void setPasswordShouldUpdatePassword() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setPassword("secret");

        assertThat(dto.getPassword()).isEqualTo("secret");
    }
}