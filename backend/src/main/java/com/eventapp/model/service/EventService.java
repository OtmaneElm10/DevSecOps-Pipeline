package com.eventapp.model.service;

import com.eventapp.model.entities.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 * Event service.
 */
@Service
public class EventService {

    private final List<Event> events = new ArrayList<>();

    /**
    * constructor.
    */
    public EventService() {
        
        long now = System.currentTimeMillis();
        
        events.add(new Event(
                "Gala du club",
                "Grande soirée annuelle",
                "Palais des Congrès",
                new Date(now),
                new Date(now + 3600000 * 5),
                500,
                15.50f
        ));

        events.add(new Event(
                "Stage M18",
                "Stage de perfectionnement pour les jeunes",
                "Stade Municipal",
                new Date(now + 86400000),
                new Date(now + 86400000 + 7200000),
                30,
                0.0f
        ));

        events.add(new Event(
                "Match National",
                "Match de championnat contre l'équipe rivale",
                "Stade National",
                new Date(now),
                new Date(now + 3600000 * 5),
                500,
                15.50f
        ));

        events.add(new Event(
                "Tournoi",
                "Tournoi amical entre clubs locaux",
                "Complexe Sportif",
                new Date(now + 86400000),
                new Date(now + 86400000 + 7200000),
                30,
                10.0f
        ));
    }

    public List<Event> getAllEvents() {
        return events;
    }

    
    /**
     * Create new event.
     * @param event event
     */
    public Event createEvent(final Event event) {
        events.add(event);
        return event;
    }
}

