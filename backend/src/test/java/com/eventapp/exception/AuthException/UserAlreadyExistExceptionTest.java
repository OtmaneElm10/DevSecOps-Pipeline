package com.eventapp.exception.AuthException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class UserAlreadyExistsExceptionTest {

    @Test
    void constructorShouldSetMessage() {
        UserAlreadyExistsException ex = new UserAlreadyExistsException("Username déjà utilisé");

        assertThat(ex.getMessage()).isEqualTo("Username déjà utilisé");
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        UserAlreadyExistsException ex = new UserAlreadyExistsException("test");

        assertThat(ex).isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldBeThrownAndCaught() {
        assertThatThrownBy(() -> { throw new UserAlreadyExistsException("Username déjà utilisé"); })
            .isInstanceOf(UserAlreadyExistsException.class)
            .hasMessage("Username déjà utilisé");
    }
}