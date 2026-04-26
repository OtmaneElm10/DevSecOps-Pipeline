package com.eventapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventapp.model.dto.PaiementResponseDto;
import com.eventapp.model.enums.PaiementStatut;
import com.eventapp.model.service.PaiementService;




/**
 * Controller class for managing Paiement entities.
 */
@RestController
@RequestMapping("/api/paiements")
public class PaiementController {
    
    private final PaiementService paiementService;

    /**
     * Constructor for PaiementController.
     * @param paiementService the service for managing Paiement entities.
     */
    public PaiementController(final PaiementService paiementService) {
        this.paiementService = paiementService;
    }


    @GetMapping
    public ResponseEntity<List<PaiementResponseDto>> getAllPaiements() {
        return ResponseEntity.ok(paiementService.getAllPaiements());
    }

    /**
     * get a Paiement by its ID.
     * @param id the ID of the Paiement to be retrieved.
     * @return the Paiement entity with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaiementResponseDto> getPaiementById(@PathVariable final Long id) {
        return ResponseEntity.ok(paiementService.getPaiementById(id));
    }

    /**
     * get the status of a Paiement by its ID.
     * @param id the ID of the Paiement whose status is to be retrieved.
     * @return the status of the Paiement with the specified ID.
     */
    @GetMapping("/{id}/status")
    public ResponseEntity<PaiementStatut> getPaiementStatus(@PathVariable final Long id) {
        return ResponseEntity.ok(paiementService.getPaiementById(id).getStatut());
    }

    /**
     * get a list of Paiement entities by the user ID associated.
     * @param userId the ID of the user whose payements are to be retrieved.
     * @return a list of all Paiement entities for the given user ID.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaiementResponseDto>> getPaiementsByUser(
        final @PathVariable Long userId) {

        return ResponseEntity.ok(
            paiementService.getPaiementsByUserId(userId)
        );
    }
    
    /**
     * pay a reservation by creating a Paiement entity.
     * @param reservationId the ID of the reservation to be paid.
     * @return the dto of the created Paiement entity.
     */
    @PostMapping("/reservation/{reservationId}/pay")
    public ResponseEntity<PaiementResponseDto> payReservation(
        @PathVariable final Long reservationId) {
        return ResponseEntity.ok(paiementService.payReservation(reservationId));
    }


}
