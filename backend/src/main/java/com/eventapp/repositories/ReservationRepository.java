package com.eventapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventapp.model.entities.Reservation;


/**
 * Repository interface for Reservation entity, providing CRUD operations.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}

