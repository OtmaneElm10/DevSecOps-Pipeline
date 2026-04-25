package com.eventapp.exception.AuthException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class InvalidPasswordExceptionTest {

    @Test
    void constructorShouldSetDefaultMessage() {
        InvalidPasswordException ex = new InvalidPasswordException();

        assertThat(ex.getMessage()).isEqualTo("Invalid password");
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        InvalidPasswordException ex = new InvalidPasswordException();

        assertThat(ex).isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldBeThrownAndCaught() {
        assertThatThrownBy(() -> { throw new InvalidPasswordException(); })
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("Invalid password");
    }
}