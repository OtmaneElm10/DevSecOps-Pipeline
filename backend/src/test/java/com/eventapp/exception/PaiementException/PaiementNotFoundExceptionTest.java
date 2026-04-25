package com.eventapp.exception.PaiementException;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PaiementNotFoundExceptionTest {
    @Test
    void constructorShouldSetDefaultMessage() {
        PaiementNotFoundException ex = new PaiementNotFoundException();

        assertThat(ex.getMessage()).isEqualTo("Paiement not found");
    }

    @Test
    void exceptionShouldBeInstanceOfRuntimeException() {
        PaiementNotFoundException ex = new PaiementNotFoundException();

        assertThat(ex).isInstanceOf(RuntimeException.class);
    }
}
