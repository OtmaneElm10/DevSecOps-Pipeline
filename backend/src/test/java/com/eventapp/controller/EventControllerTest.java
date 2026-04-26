package com.eventapp.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.eventapp.exception.EventException.EventNotFoundException;
import com.eventapp.model.entities.Event;
import com.eventapp.model.entities.User;
import com.eventapp.model.enums.EventType;
import com.eventapp.model.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class EventControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
        Event e1 = createTestEvent(1L, "Gala du club", EventType.SOIREE);
        Event e2 = createTestEvent(2L, "Stage M18", EventType.STAGE);
        Event e3 = createTestEvent(3L, "Match National", EventType.MATCH);
        Event e4 = createTestEvent(4L, "Tournoi", EventType.TOURNOI);

        List<Event> events = List.of(e1, e2, e3, e4);

        given(eventService.getAllEvents()).willReturn(events);

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].title").value("Gala du club"))
                .andExpect(jsonPath("$[1].title").value("Stage M18"));
    }

    @Test
    void getEventsByTypeShouldReturnFilteredEvents() throws Exception {
        Event match1 = createTestEvent(1L, "Match 1", EventType.MATCH);
        Event match2 = createTestEvent(2L, "Match 2", EventType.MATCH);

        given(eventService.getEventsByType(EventType.MATCH)).willReturn(List.of(match1, match2));

        mockMvc.perform(get("/api/events?type=MATCH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].type").value("MATCH"))
                .andExpect(jsonPath("$[1].type").value("MATCH"));
    }

    @Test
    void getEventsByTypeShouldReturnEmptyListWhenNoEvents() throws Exception {
        given(eventService.getEventsByType(EventType.STAGE)).willReturn(List.of());

        mockMvc.perform(get("/api/events?type=STAGE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getEventByIdShouldReturnEvent() throws Exception {
        Event event = createTestEvent(1L, "Test Event", EventType.MATCH);

        given(eventService.getEventById(1L)).willReturn(event);

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Event"))
                .andExpect(jsonPath("$.type").value("MATCH"));
    }

    @Test
    void getEventByIdShouldReturn404WhenNotFound() throws Exception {
        given(eventService.getEventById(1L)).willThrow(new EventNotFoundException());

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createEventShouldReturn201() throws Exception {
        Event eventToCreate = createTestEvent(null, "New Event", EventType.SOIREE);
        Event createdEvent = createTestEvent(1L, "New Event", EventType.SOIREE);

        given(eventService.createEvent(eventToCreate)).willReturn(createdEvent);

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Event"));
    }

    @Test
    void updateEventShouldReturn200() throws Exception {
        Event updateData = createTestEvent(null, "Updated Event", EventType.TOURNOI);
        Event updatedEvent = createTestEvent(1L, "Updated Event", EventType.TOURNOI);

        given(eventService.updateEvent(1L, updateData)).willReturn(updatedEvent);

        mockMvc.perform(put("/api/events/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Event"))
                .andExpect(jsonPath("$.type").value("TOURNOI"));
    }

    @Test
    void updateEventShouldReturn404WhenNotFound() throws Exception {
        Event updateData = createTestEvent(null, "Updated Event", EventType.TOURNOI);

        given(eventService.updateEvent(1L, updateData)).willThrow(new EventNotFoundException());

        mockMvc.perform(put("/api/events/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteEventShouldReturn204() throws Exception {
        doNothing().when(eventService).deleteEvent(1L);

        mockMvc.perform(delete("/api/events/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteEventShouldReturn404WhenNotFound() throws Exception {
        doThrow(new EventNotFoundException()).when(eventService).deleteEvent(1L);

        mockMvc.perform(delete("/api/events/1"))
                .andExpect(status().isNotFound());
    }

    private Event createTestEvent(Long id, String title, EventType type) {
        Event event = new Event();
        event.setId(id);
        event.setTitle(title);
        event.setDescription("Test Description");
        event.setLieu("Test Location");
        event.setDateDebut(LocalDate.now());
        event.setDateFin(LocalDate.now().plusDays(1));
        event.setCapaciteMax(100);
        event.setPrix(25.0);
        event.setType(type);
        event.setCreatedBy(new User());
        return event;
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
