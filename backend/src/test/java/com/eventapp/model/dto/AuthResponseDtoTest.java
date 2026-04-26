package com.eventapp.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AuthResponseDtoTest {

    @Test
    void defaultConstructorShouldAllowSettersAndGetters() {
        AuthResponseDto dto = new AuthResponseDto();
        dto.setToken("token");
        dto.setId(5L);
        dto.setUsername("user");
        dto.setEmail("user@example.com");
        dto.setRole("ADMIN");

        assertEquals("token", dto.getToken());
        assertEquals(5L, dto.getId());
        assertEquals("user", dto.getUsername());
        assertEquals("user@example.com", dto.getEmail());
        assertEquals("ADMIN", dto.getRole());
    }

    @Test
    void fullConstructorShouldInitializeAllFields() {
        AuthResponseDto dto = new AuthResponseDto(
            "token",
            5L,
            "user",
            "user@example.com",
            "ADMIN"
        );

        assertEquals("token", dto.getToken());
        assertEquals(5L, dto.getId());
        assertEquals("user", dto.getUsername());
        assertEquals("user@example.com", dto.getEmail());
        assertEquals("ADMIN", dto.getRole());
    }
}
