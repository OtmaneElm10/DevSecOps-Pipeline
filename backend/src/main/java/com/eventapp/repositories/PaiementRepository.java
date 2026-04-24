package com.eventapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventapp.model.entities.Paiement;


/**
 * Repository interface for managing Paiement entities. 
 * This interface extends JpaRepository, 
 * providing CRUD operations and additional query methods for the Paiement entity.
 */
public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    /**
     * Find a Paiement entity by its associated Reservation ID.
     * @param reservationId the ID of the associated Reservation.
     * @return an Optional containing the found Paiement entity, or empty if not found.
     */
    Optional<Paiement> findByReservationId(Long reservationId);


    /**
     * Find a list of Paiement entities by the user ID associated.
     * @param userId the ID of the user whose payements are to be retrieved.
     * @return a list of all Paiement entities for the given user ID.
     */
    List<Paiement> findByReservationUserId(Long userId);
    
}
