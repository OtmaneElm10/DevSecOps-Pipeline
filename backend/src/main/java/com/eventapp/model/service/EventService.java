package com.eventapp.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventapp.model.entities.Event;
import com.eventapp.repositories.EventRepository;

/**
 * Event service.
 */
@Service
public class EventService {

    private final EventRepository eventRepository;

    /**
     * Constructor.
     *
     * @param eventRepository event repository
     */
    public EventService(final EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Returns all events.
     *
     * @return list of events
     */
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Creates a new event.
     *
     * @param event event to save
     * @return saved event
     */
    public Event createEvent(final Event event) {
        return eventRepository.save(event);
    }
}

