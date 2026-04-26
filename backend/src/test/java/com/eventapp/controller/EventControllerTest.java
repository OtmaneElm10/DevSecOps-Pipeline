package com.eventapp.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.eventapp.model.entities.Event;
import com.eventapp.model.enums.EventType;
import com.eventapp.model.service.EventService;

@SpringBootTest
class EventControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

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

        Event e1 = new Event();
        e1.setTitle("Gala du club");
        e1.setDescription("Grande soirée annuelle");
        e1.setLieu("Salle des Fêtes");
        e1.setDateDebut(LocalDate.now());
        e1.setDateFin(LocalDate.now().plusDays(1));
        e1.setCapaciteMax(500);
        e1.setPrix(15.5);
        e1.setType(EventType.SOIREE);

        Event e2 = new Event();
        e2.setTitle("Stage M18");
        e2.setDescription("Stage de perfectionnement pour les joeurs");
        e2.setLieu("Stade Municipal");
        e2.setDateDebut(LocalDate.now());
        e2.setDateFin(LocalDate.now().plusDays(1));
        e2.setCapaciteMax(30);
        e2.setPrix(0.0);
        e2.setType(EventType.STAGE);

        Event e3 = new Event();
        e3.setTitle("Match National");
        e3.setDescription("Match de championnat contre l'équipe rivale");
        e3.setLieu("Stade National");
        e3.setDateDebut(LocalDate.now());
        e3.setDateFin(LocalDate.now().plusDays(1));
        e3.setCapaciteMax(500);
        e3.setPrix(15.5);
        e3.setType(EventType.MATCH);

        Event e4 = new Event();
        e4.setTitle("Tournoi");
        e4.setDescription("Tournoi amical entre clubs locaux");
        e4.setLieu("Complexe Sportif");
        e4.setDateDebut(LocalDate.now());
        e4.setDateFin(LocalDate.now().plusDays(1));
        e4.setCapaciteMax(30);
        e4.setPrix(10.0);
        e4.setType(EventType.TOURNOI);

        List<Event> events = List.of(e1, e2, e3, e4);

        given(eventService.getAllEvents()).willReturn(events);

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    void getAllEventsShouldContainGala() throws Exception {

        Event e = new Event();
        e.setTitle("Gala du club");
        e.setDescription("Grande soirée annuelle");
        e.setLieu("Salle des Fêtes");
        e.setDateDebut(LocalDate.now());
        e.setDateFin(LocalDate.now().plusDays(1));
        e.setCapaciteMax(500);
        e.setPrix(15.5);
        e.setType(EventType.SOIREE);

        given(eventService.getAllEvents()).willReturn(List.of(e));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Gala du club"))
                .andExpect(jsonPath("$[0].lieu").value("Salle des Fêtes"))
                .andExpect(jsonPath("$[0].capaciteMax").value(500));
    }
}
