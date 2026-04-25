package com.eventapp.exception.ReservationException;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReservationCapacityExceededExceptionTest {
    @Test
    void constructorShouldSetDefaultMessage() {
        ReservationCapacityExceededException ex = new ReservationCapacityExceededException();

        assertThat(ex.getMessage()).isEqualTo("Not enough available places for this event");
    }

    @Test
    void exceptionShouldBeInstanceOfRuntimeException() {
        ReservationCapacityExceededException ex = new ReservationCapacityExceededException();

        assertThat(ex).isInstanceOf(RuntimeException.class);
    }
}
