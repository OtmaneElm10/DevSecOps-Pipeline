package com.eventapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventapp.model.entities.Reservation;


/**
 * Repository interface for Reservation entity, providing CRUD operations.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Finds reservations by user ID.
     * @param userId user ID
     * @return list of reservations for the specified user ID
     */
    List<Reservation> findByUserId(Long userId);

    
    /**
     * Finds reservations by event ID.
     * @param eventId event ID
     * @return list of reservations for the specified event ID
     */
    List<Reservation> findByEventId(Long eventId);

   
}

