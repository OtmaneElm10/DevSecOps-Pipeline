package com.eventapp.model.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


class RegisterRequestDtoTest {

    @Test
    void defaultConstructorShouldCreateEmptyDto() {
        RegisterRequestDto dto = new RegisterRequestDto();

        assertThat(dto.getUsername()).isNull();
        assertThat(dto.getEmail()).isNull();
        assertThat(dto.getPassword()).isNull();
    }

    @Test
    void settersShouldUpdateAllFields() {
        RegisterRequestDto dto = new RegisterRequestDto();

        dto.setUsername("john");
        dto.setEmail("john@test.com");
        dto.setPassword("pass123");

        assertThat(dto.getUsername()).isEqualTo("john");
        assertThat(dto.getEmail()).isEqualTo("john@test.com");
        assertThat(dto.getPassword()).isEqualTo("pass123");
    }

    @Test
    void setUsernameShouldUpdateUsername() {
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setUsername("alice");

        assertThat(dto.getUsername()).isEqualTo("alice");
    }

    @Test
    void setEmailShouldUpdateEmail() {
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setEmail("alice@test.com");

        assertThat(dto.getEmail()).isEqualTo("alice@test.com");
    }

    @Test
    void setPasswordShouldUpdatePassword() {
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setPassword("secret");

        assertThat(dto.getPassword()).isEqualTo("secret");
    }
}