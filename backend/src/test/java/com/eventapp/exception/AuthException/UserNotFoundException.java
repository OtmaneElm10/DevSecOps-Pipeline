package com.eventapp.exception.AuthException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserNotFoundExceptionTest {

    private UserNotFoundException exception;

    @BeforeEach
    void setUp() {
        exception = new UserNotFoundException();
    }

    @Test
    void constructorShouldSetDefaultMessage() {
        assertThat(exception.getMessage()).isEqualTo("User not found");
    }

    @Test
    void messageContentShouldNotBeNull() {
        assertThat(exception.getMessage()).isNotNull();
        assertThat(exception.getMessage()).isNotEmpty();
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldBeInstanceOfException() {
        assertThat(exception).isInstanceOf(Exception.class);
    }

    @Test
    void shouldBeInstanceOfThrowable() {
        assertThat(exception).isInstanceOf(Throwable.class);
    }

    @Test
    void shouldBeInstanceOfUserNotFoundException() {
        assertThat(exception).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldBeThrownAndCaught() {
        assertThatThrownBy(() -> { 
            throw new UserNotFoundException(); })
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("User not found");
    }

    @Test
    void stackTraceShouldBeGenerated() {
        assertThat(exception.getStackTrace()).isNotNull();
        assertThat(exception.getStackTrace()).isNotEmpty();
    }

    @Test
    void getLocalizedMessageShouldReturnMessage() {
        assertThat(exception.getLocalizedMessage()).isEqualTo("User not found");
    }

    @Test
    void toStringShouldContainClassName() {
        String exceptionString = exception.toString();
        assertThat(exceptionString).contains("UserNotFoundException");
        assertThat(exceptionString).contains("User not found");
    }

    @Test
    void causeShouldBeNull() {
        assertThat(exception.getCause()).isNull();
    }

    @Test
    void multipleInstancesShouldHaveSameMessage() {
        UserNotFoundException ex1 = new UserNotFoundException();
        UserNotFoundException ex2 = new UserNotFoundException();

        assertThat(ex1.getMessage()).isEqualTo(ex2.getMessage());
    }

    @Test
    void shouldThrowWhenUserAuthenticationFails() {
        assertThatThrownBy(() -> {
            throw new UserNotFoundException();
        })
        .isInstanceOf(UserNotFoundException.class)
        .hasNoCause();
    }

    @Test
    void shouldThrowInTryCatchBlock() {
        boolean exceptionCaught = false;
        try {
            throw new UserNotFoundException();
        } catch (UserNotFoundException e) {
            exceptionCaught = true;
            assertThat(e.getMessage()).isEqualTo("User not found");
        }

        assertThat(exceptionCaught).isTrue();
    }

    @Test
    void shouldThrowWithCorrectMessageInMultipleCatches() {
        boolean userNotFoundCaught = false;
        boolean runtimeExceptionCaught = false;

        try {
            throw new UserNotFoundException();
        } catch (UserNotFoundException e) {
            userNotFoundCaught = true;
        } catch (RuntimeException e) {
            runtimeExceptionCaught = true;
        }

        assertThat(userNotFoundCaught).isTrue();
        assertThat(runtimeExceptionCaught).isFalse();
    }
}
