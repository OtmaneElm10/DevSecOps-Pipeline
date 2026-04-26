package com.eventapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventapp.model.entities.Event;
import com.eventapp.model.enums.EventType;
import com.eventapp.model.service.EventService;



/** 
 * Event controller api. 
 */
@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    /**
     * constructor.
     * @param eventService
     */
    public EventController(final EventService eventService) {
        this.eventService = eventService;
    }

    
    @GetMapping(params = "!type")
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    /**
     * Get events by type.
     * @param type event type
     * @return list of events with the specified type
     */
    @GetMapping(params = "type")
    public ResponseEntity<List<Event>> getEventsByType(@RequestParam final EventType type) {
        return ResponseEntity.ok(eventService.getEventsByType(type));
    
    }

    /**
     * Get event by ID.
     * @param id event ID
     * @return event with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable final Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }


    /**
     * Create new event.
     * @param event event
     */
    @PostMapping
    public ResponseEntity<Event> createEvent(final @RequestBody Event event) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(eventService.createEvent(event));
    }

    
    /**
     * Update an existing event.
     * @param id event ID to update
     * @param event event data to update
     * @return updated event
     */
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable final Long id, 
        final @RequestBody Event event) {
        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }

    
    /**
     * Delete an event by ID.
     * @param id event ID to delete
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable final Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}
