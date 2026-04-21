package com.eventapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventapp.model.entities.Event;
import com.eventapp.model.entities.EventType;


/**
 * Repository interface for Event entity, providing CRUD operations and custom query methods.
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Find events by their type.
     * @param type type
     * @return list of events with the specified type
     */
    List<Event> findByType(EventType type);
}
