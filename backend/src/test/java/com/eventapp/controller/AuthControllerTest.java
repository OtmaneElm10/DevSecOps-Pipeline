package com.eventapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.eventapp.model.dto.AuthResponseDto;
import com.eventapp.model.dto.LoginRequestDto;
import com.eventapp.model.dto.RegisterRequestDto;
import com.eventapp.model.entities.User;
import com.eventapp.model.service.AuthService;
import com.eventapp.security.JwtService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private JwtService jwtService;

    private AuthController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthController(authService, jwtService);
    }

    @Test
    void registerShouldReturnAuthResponse() {
        User user = new User("testpseudo", "test@example.com", "password", "USER");
        user.setId(1L);

        RegisterRequestDto request = new RegisterRequestDto();
        request.setUsername("testpseudo");
        request.setEmail("test@example.com");
        request.setPassword("password");

        given(authService.register("testpseudo", "test@example.com", "password"))
                .willReturn(user);
        given(jwtService.generateToken(user)).willReturn("fake-token");

        ResponseEntity<AuthResponseDto> response = authController.register(request);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());
        assertEquals("testpseudo", response.getBody().getUsername());
        assertEquals("test@example.com", response.getBody().getEmail());
        assertEquals("USER", response.getBody().getRole());
        assertEquals("fake-token", response.getBody().getToken());

        verify(authService, times(1))
                .register("testpseudo", "test@example.com", "password");
        verify(jwtService, times(1)).generateToken(user);
    }

    @Test
    void loginShouldReturnAuthResponse() {
        User user = new User("testpseudo", "test@example.com", "password", "USER");
        user.setId(1L);

        LoginRequestDto request = new LoginRequestDto();
        request.setUsername("testpseudo");
        request.setPassword("password");

        given(authService.login("testpseudo", "password")).willReturn(user);
        given(jwtService.generateToken(user)).willReturn("fake-token");

        ResponseEntity<AuthResponseDto> response = authController.login(request);

        assertEquals(200, response.getStatusCode().value());

        AuthResponseDto result = response.getBody();
        assertEquals(1L, result.getId());
        assertEquals("testpseudo", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("USER", result.getRole());
        assertEquals("fake-token", result.getToken());

        verify(authService, times(1)).login("testpseudo", "password");
        verify(jwtService, times(1)).generateToken(user);
    }
}
