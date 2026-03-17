package com.eventapp.controller;

import com.eventapp.model.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.sql.Date;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*") // On accepte tout pour faciliter tes tests front
public class EventController {

    @GetMapping
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        long now = System.currentTimeMillis();

        // On ajoute des événements manuellement dans la liste
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


        return events;
    }
}