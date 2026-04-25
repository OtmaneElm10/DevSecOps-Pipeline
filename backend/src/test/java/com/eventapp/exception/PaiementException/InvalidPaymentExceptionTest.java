package com.eventapp.exception.PaiementException;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class InvalidPaymentExceptionTest {
    @Test
    void constructorShouldSetCustomMessage() {
        String customMessage = "Payment data is invalid";
        InvalidPaymentException ex = new InvalidPaymentException(customMessage);

        assertThat(ex.getMessage()).isEqualTo(customMessage);
    }

    @Test
    void exceptionShouldBeInstanceOfRuntimeException() {
        InvalidPaymentException ex = new InvalidPaymentException("Test message");

        assertThat(ex).isInstanceOf(RuntimeException.class);
    }

    @Test
    void constructorShouldHandleEmptyMessage() {
        InvalidPaymentException ex = new InvalidPaymentException("");

        assertThat(ex.getMessage()).isEmpty();
    }

    @Test
    void constructorShouldHandleNullMessage() {
        InvalidPaymentException ex = new InvalidPaymentException(null);

        assertThat(ex.getMessage()).isNull();
    }
}
