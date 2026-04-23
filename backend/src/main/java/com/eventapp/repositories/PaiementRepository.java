package com.eventapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventapp.model.entities.Paiement;


/**
 * Repository interface for managing Paiement entities. 
 * This interface extends JpaRepository, 
 * providing CRUD operations and additional query methods for the Paiement entity.
 */
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    
}
