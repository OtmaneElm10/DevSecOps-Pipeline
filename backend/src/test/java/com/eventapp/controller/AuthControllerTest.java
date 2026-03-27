package com.eventapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.eventapp.model.entities.User;
import com.eventapp.model.service.AuthService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    private AuthController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthController(authService);
    }

    @Test
    void registerShouldReturnUser() {
        User user = new User("testpseudo", "test@example.com", "password", "USER");
        user.setId(1L);

        given(authService.register("testpseudo", "test@example.com", "password")).willReturn(user);

        Map<String, String> requestBody = Map.of(
            "username", "testpseudo",
            "email", "test@example.com",
            "Password", "password"
        );

        User result = authController.register(requestBody);

        assertEquals(user.getId(), result.getId());
        assertEquals("testpseudo", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        verify(authService, times(1)).register("testpseudo", "test@example.com", "password");
    }

    @Test
    void loginShouldReturnUser() {
        User user = new User("testpseudo", "test@example.com", "password", "USER");
        user.setId(1L);

        given(authService.login("testpseudo", "password")).willReturn(user);

        Map<String, String> requestBody = Map.of(
            "username", "testpseudo",
            "password", "password"
        );

        User result = authController.login(requestBody);

        assertEquals(user.getId(), result.getId());
        assertEquals("testpseudo", result.getUsername());
        verify(authService, times(1)).login("testpseudo", "password");
    }
}