package com.eventapp.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.eventapp.model.entities.Event;
import com.eventapp.model.service.EventService;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    void getAllEventsShouldReturn200() throws Exception {
        given(eventService.getAllEvents()).willReturn(List.of());

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllEventsShouldReturnList() throws Exception {
        long now = System.currentTimeMillis();

        List<Event> events = List.of(
                new Event("Gala du club", "Grande soirée annuelle", "Palais des Congrès",
                        new Date(now), new Date(now + 1000), 500, 15.5f),
                new Event("Stage M18", 
                "Stage de perfectionnement pour les jeunes", "Stade Municipal",
                        new Date(now), new Date(now + 1000), 30, 0.0f),
                new Event("Match National", 
                "Match de championnat contre l'équipe rivale", "Stade National",
                        new Date(now), new Date(now + 1000), 500, 15.5f),
                new Event("Tournoi", "Tournoi amical entre clubs locaux", "Complexe Sportif",
                        new Date(now), new Date(now + 1000), 30, 10.0f)
        );

        given(eventService.getAllEvents()).willReturn(events);

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    void getAllEventsShouldContainGala() throws Exception {
        long now = System.currentTimeMillis();

        List<Event> events = List.of(
                new Event("Gala du club", "Grande soirée annuelle", "Palais des Congrès",
                        new Date(now), new Date(now + 1000), 500, 15.5f)
        );

        given(eventService.getAllEvents()).willReturn(events);

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Gala du club"))
                .andExpect(jsonPath("$[0].lieu").value("Palais des Congrès"))
                .andExpect(jsonPath("$[0].capaciteMax").value(500));
    }
}
