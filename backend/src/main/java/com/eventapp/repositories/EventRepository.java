package com.eventapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventapp.model.entities.Event;

/**
 * Repository for Event entity.
 */
public interface EventRepository extends JpaRepository<Event, Long> {
}

