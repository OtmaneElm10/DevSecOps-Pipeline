package com.eventapp.exception.AuthException;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


class InvalidPasswordExceptionTest {
    @Test
    void constructorShouldSetDefaultMessage() {
        InvalidPasswordException ex = new InvalidPasswordException();

        assertThat(ex.getMessage()).isEqualTo("Invalid password");
    }
}
