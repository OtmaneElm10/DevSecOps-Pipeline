package com.eventapp.exception.EventException;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EventNotFoundExceptionTest {
    @Test
    void constructorShouldSetDefaultMessage() {
        EventNotFoundException ex = new EventNotFoundException();

        assertThat(ex.getMessage()).isEqualTo("Event not found");
    }
}
