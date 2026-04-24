package com.eventapp.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.eventapp.exception.AuthException.UserNotFoundException;
import com.eventapp.exception.EventException.EventNotFoundException;

class GlobalExceptionHandlerTest {
    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleUserNotFoundShouldReturn404() {
        UserNotFoundException exception = new UserNotFoundException();

        ResponseEntity<Map<String, String>> response = handler.handleUserNotFound(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).containsEntry("message", exception.getMessage());
    }

    @Test
    void handleEventNotFoundShouldReturn404() {
        EventNotFoundException exception = new EventNotFoundException();

        ResponseEntity<Map<String, String>> response = handler.handleEventNotFound(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).containsEntry("message", exception.getMessage());
    }

    @Test
    void handleUserNotFoundShouldIncludeExceptionMessage() {
        UserNotFoundException exception = new UserNotFoundException();

        ResponseEntity<Map<String, String>> response = handler.handleUserNotFound(exception);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("message")).isNotBlank();
    }

    @Test
    void handleEventNotFoundShouldIncludeExceptionMessage() {
        EventNotFoundException exception = new EventNotFoundException();

        ResponseEntity<Map<String, String>> response = handler.handleEventNotFound(exception);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("message")).isNotBlank();
    }
}
