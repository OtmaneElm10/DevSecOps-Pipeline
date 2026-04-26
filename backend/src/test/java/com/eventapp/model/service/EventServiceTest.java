package com.eventapp.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.eventapp.exception.EventException.EventNotFoundException;
import com.eventapp.model.entities.Event;
import com.eventapp.model.entities.User;
import com.eventapp.model.enums.EventType;
import com.eventapp.repositories.EventRepository;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void getAllEventsShouldReturnList() {
        Event event = createTestEvent();
        given(eventRepository.findAll()).willReturn(List.of(event));

        List<Event> result = eventService.getAllEvents();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Event");
    }

    @Test
    void getAllEventsShouldReturnEmptyListWhenNoEvents() {
        given(eventRepository.findAll()).willReturn(List.of());

        List<Event> result = eventService.getAllEvents();

        assertThat(result).isEmpty();
    }

    @Test
    void getEventsByTypeShouldReturnFilteredEvents() {
        Event matchEvent = createTestEvent();
        matchEvent.setType(EventType.MATCH);
        Event tournoiEvent = createTestEvent();
        tournoiEvent.setType(EventType.TOURNOI);
        tournoiEvent.setTitle("Tournoi Event");

        given(eventRepository.findByType(EventType.MATCH)).willReturn(List.of(matchEvent));

        List<Event> result = eventService.getEventsByType(EventType.MATCH);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getType()).isEqualTo(EventType.MATCH);
    }

    @Test
    void getEventsByTypeShouldReturnEmptyListWhenNoEventsOfType() {
        given(eventRepository.findByType(EventType.STAGE)).willReturn(List.of());

        List<Event> result = eventService.getEventsByType(EventType.STAGE);

        assertThat(result).isEmpty();
    }

    @Test
    void getEventByIdShouldReturnEventWhenFound() {
        Event event = createTestEvent();
        given(eventRepository.findById(1L)).willReturn(Optional.of(event));

        Event result = eventService.getEventById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Event");
    }

    @Test
    void getEventByIdShouldThrowExceptionWhenNotFound() {
        given(eventRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.getEventById(1L))
            .isInstanceOf(EventNotFoundException.class)
            .hasMessage("Event not found");
    }

    @Test
    void createEventShouldReturnSavedEvent() {
        Event eventToCreate = createTestEvent();
        eventToCreate.setId(null); 
        Event savedEvent = createTestEvent();
        savedEvent.setId(1L);

        given(eventRepository.save(eventToCreate)).willReturn(savedEvent);

        Event result = eventService.createEvent(eventToCreate);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test Event");
    }

    @Test
    void updateEventShouldReturnUpdatedEventWhenFound() {
        Event existingEvent = createTestEvent();
        existingEvent.setId(1L);
        Event updatedEvent = createTestEvent();
        updatedEvent.setTitle("Updated Title");
        updatedEvent.setDescription("Updated Description");

        given(eventRepository.findById(1L)).willReturn(Optional.of(existingEvent));
        given(eventRepository.save(any(Event.class))).willReturn(existingEvent);

        Event result = eventService.updateEvent(1L, updatedEvent);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(eventRepository).save(existingEvent);
    }

    @Test
    void updateEventShouldThrowExceptionWhenNotFound() {
        Event updatedEvent = createTestEvent();
        given(eventRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.updateEvent(1L, updatedEvent))
            .isInstanceOf(EventNotFoundException.class)
            .hasMessage("Event not found");
    }

    @Test
    void deleteEventShouldDeleteWhenFound() {
        given(eventRepository.existsById(1L)).willReturn(true);
        doNothing().when(eventRepository).deleteById(1L);

        eventService.deleteEvent(1L);

        verify(eventRepository).deleteById(1L);
    }

    @Test
    void deleteEventShouldThrowExceptionWhenNotFound() {
        given(eventRepository.existsById(1L)).willReturn(false);

        assertThatThrownBy(() -> eventService.deleteEvent(1L))
            .isInstanceOf(EventNotFoundException.class)
            .hasMessage("Event not found");
    }

    private Event createTestEvent() {
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Test Event");
        event.setDescription("Test Description");
        event.setLieu("Test lieu");
        event.setDateDebut(LocalDate.of(2023, 10, 1));
        event.setDateFin(LocalDate.of(2023, 10, 2));
        event.setCapaciteMax(100);
        event.setPrix(50.0);
        event.setType(EventType.MATCH);
        event.setCreatedBy(new User());
        return event;
    }
}

