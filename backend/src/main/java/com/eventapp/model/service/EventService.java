package com.eventapp.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.eventapp.exception.EventException.EventNotFoundException;

import com.eventapp.model.entities.Event;
import com.eventapp.model.entities.EventType;
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
     * Returns events by type.
     * @param type event type
     * @return list of events with the specified type
     */
    public List<Event> getEventsByType(final EventType type) {
        return eventRepository.findByType(type);
    }


    /**
     * Returns an event by its ID.
     * @param id event ID
     * @return event with the specified ID
     */
    public Event getEventById(final Long id) {
        return eventRepository.findById(id)
                .orElseThrow(EventNotFoundException::new);
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


    /**
     * Updates an existing event.
     * @param id event ID to update
     * @param updatedEvent event data to update
     * @return updated event
     */
    public Event updateEvent(final Long id, final Event updatedEvent) {
        Event event = eventRepository.findById(id)
            .orElseThrow(EventNotFoundException::new);

        event.setTitle(updatedEvent.getTitle());
        event.setDescription(updatedEvent.getDescription());
        event.setLieu(updatedEvent.getLieu());
        event.setDateDebut(updatedEvent.getDateDebut());
        event.setDateFin(updatedEvent.getDateFin());
        event.setCapaciteMax(updatedEvent.getCapaciteMax());
        event.setPrix(updatedEvent.getPrix());
        event.setType(updatedEvent.getType());
        event.setCreatedBy(updatedEvent.getCreatedBy());

        return eventRepository.save(event);

    }


    /**
     * Deletes an event by its ID.
     * @param id event ID to delete
     */
    public void deleteEvent(final Long id) {
        
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException();
        }

        eventRepository.deleteById(id);
    }
}

