package com.eventapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventapp.model.entities.Event;
import com.eventapp.model.service.EventService;



/** 
 * Event controller api. 
 */
@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    private final EventService eventService;

    /**
     * constructor.
     * @param eventService
     */
    public EventController(final EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    
    /**
     * Create new event.
     * @param event event
     */
    @PostMapping
    public Event createEvent(final @RequestBody Event event) {
        return eventService.createEvent(event);
    }
}

