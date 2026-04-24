package com.eventapp.exception.ReservationException;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class InvalidReservationExceptionTest {
    @Test
    void constructorShouldSetCustomMessage() {
        String customMessage = "Reservation data is invalid";
        InvalidReservationException ex = new InvalidReservationException(customMessage);

        assertThat(ex.getMessage()).isEqualTo(customMessage);
    }

    @Test
    void exceptionShouldBeInstanceOfRuntimeException() {
        InvalidReservationException ex = new InvalidReservationException("Test message");

        assertThat(ex).isInstanceOf(RuntimeException.class);
    }

    @Test
    void constructorShouldHandleEmptyMessage() {
        InvalidReservationException ex = new InvalidReservationException("");

        assertThat(ex.getMessage()).isEmpty();
    }

    @Test
    void constructorShouldHandleNullMessage() {
        InvalidReservationException ex = new InvalidReservationException(null);

        assertThat(ex.getMessage()).isNull();
    }
}
