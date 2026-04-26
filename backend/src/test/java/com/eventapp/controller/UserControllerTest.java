package com.eventapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.eventapp.model.entities.User;
import com.eventapp.model.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService);
    }

    private User buildUser(final Long id, final String username) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(username + "@test.com");
        return user;
    }

    @Test
    void getUserByUsernameShouldReturn200() {
        when(userService.getByUsername("MimiMati")).thenReturn(buildUser(1L, "MimiMati"));

        ResponseEntity<User> response = userController.getUserByUsername("MimiMati");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void getUserByUsernameShouldReturnCorrectUser() {
        User user = buildUser(1L, "MimiMati");
        when(userService.getByUsername("MimiMati")).thenReturn(user);

        ResponseEntity<User> response = userController.getUserByUsername("MimiMati");

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getUsername()).isEqualTo("MimiMati");
        assertThat(response.getBody().getEmail()).isEqualTo("MimiMati@test.com");
        verify(userService).getByUsername("MimiMati");
    }
}