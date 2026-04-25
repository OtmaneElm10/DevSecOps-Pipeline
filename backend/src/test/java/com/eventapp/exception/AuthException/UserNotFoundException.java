package com.eventapp.exception.AuthException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class UserNotFoundExceptionTest {

    @Test
    void constructorShouldSetDefaultMessage() {
        UserNotFoundException ex = new UserNotFoundException();

        assertThat(ex.getMessage()).isEqualTo("User not found");
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        UserNotFoundException ex = new UserNotFoundException();

        assertThat(ex).isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldBeThrownAndCaught() {
        assertThatThrownBy(() -> { throw new UserNotFoundException(); })
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("User not found");
    }
}