package com.eventapp.controller;

import com.eventapp.model.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin; // A garder pour le front 

import java.util.List;
import java.sql.Date;

@RestController
@RequestMapping("/api/events")
// @CrossOrigin(origins = "http://localhost:3000") // Décommente cette ligne quand on utilisera le front 
public class EventController {

    @GetMapping
    public List<Event> getAllEvents() {
        
        long now = System.currentTimeMillis();
        Date dateDebut = new Date(now);
        Date dateFin = new Date(now + 86400000); // Demain (+24h)

        return List.of(
            new Event(
                "1", 
                "Gala de l'Université", 
                "Grande soirée annuelle", 
                "Palais des Congrès", 
                dateDebut, 
                dateFin, 
                500, 
                15.50f
            ),
            new Event(
                "2", 
                "Atelier Codage", 
                "Apprendre Spring Boot en 2h", 
                "Salle Info 102", 
                dateDebut, 
                dateFin, 
                30, 
                0.0f
            )
        );
    }
}