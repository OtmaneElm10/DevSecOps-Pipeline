package com.eventapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllEvents_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/events"))
            .andExpect(status().isOk());
    }

    @Test
    void getAllEvents_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/events"))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    void getAllEvents_shouldContainGala() throws Exception {
        mockMvc.perform(get("/api/events"))
            .andExpect(jsonPath("$[0].title").value("Gala du club"))
            .andExpect(jsonPath("$[0].lieu").value("Palais des Congrès"))
            .andExpect(jsonPath("$[0].capaciteMax").value(500));
    }

    @Test
    void testEndpoint_shouldReturnTest() throws Exception {
        mockMvc.perform(get("/api/test"))
            .andExpect(status().isOk())
            .andExpect(content().string("test"));
    }
}