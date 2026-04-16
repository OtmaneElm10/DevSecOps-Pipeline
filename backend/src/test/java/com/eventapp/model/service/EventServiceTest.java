package com.eventapp.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.eventapp.model.entities.Event;
import com.eventapp.repositories.EventRepository;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void getAllEventsShouldReturnList() {
        Event event = new Event();
        event.setTitle("Test");

        given(eventRepository.findAll()).willReturn(List.of(event));

        List<Event> result = eventService.getAllEvents();

        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getTitle());
    }
}

