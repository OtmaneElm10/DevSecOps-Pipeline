package com.eventapp.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.Event;

class EventServiceTest {

    private final EventService eventService = new EventService();

    @Test
    void getAllEventsShouldReturnList() {
        List<Event> events = eventService.getAllEvents();

        assertEquals(4, events.size());
        assertTrue(events.stream().anyMatch(e -> e.getTitle().equals("Gala du club")));
        assertTrue(events.stream().anyMatch(e -> e.getTitle().equals("Stage M18")));
        assertTrue(events.stream().anyMatch(e -> e.getTitle().equals("Match National")));
        assertTrue(events.stream().anyMatch(e -> e.getTitle().equals("Tournoi")));
    }

    @Test
    void createEventShouldAddEventToList() {
        long now = System.currentTimeMillis();
        Event newEvent = new Event("New Event", "Description", "Location",
                new Date(now), new Date(now + 1000), 100, 10.0f);

        Event result = eventService.createEvent(newEvent);

        assertEquals(newEvent, result);
        List<Event> events = eventService.getAllEvents();
        assertTrue(events.contains(newEvent));
    }
}