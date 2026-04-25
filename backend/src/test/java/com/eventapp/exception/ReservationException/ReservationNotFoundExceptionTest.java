package com.eventapp.exception.ReservationException;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReservationNotFoundExceptionTest {
    @Test
    void constructorShouldSetDefaultMessage() {
        ReservationNotFoundException ex = new ReservationNotFoundException();

        assertThat(ex.getMessage()).isEqualTo("Reservation not found");
    }

    @Test
    void exceptionShouldBeInstanceOfRuntimeException() {
        ReservationNotFoundException ex = new ReservationNotFoundException();

        assertThat(ex).isInstanceOf(RuntimeException.class);
    }
}
