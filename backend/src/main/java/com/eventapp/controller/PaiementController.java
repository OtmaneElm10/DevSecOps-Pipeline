package com.eventapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventapp.model.dto.PaiementCreateRequestDto;
import com.eventapp.model.dto.PaiementResponseDto;
import com.eventapp.model.service.PaiementService;




/**
 * Controller class for managing Paiement entities.
 */
@RestController
@RequestMapping("/api/paiements")
@CrossOrigin(origins = "*")
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
    public ResponseEntity<String> getPaiementStatus(@PathVariable final Long id) {
        return ResponseEntity.ok(paiementService.getPaiementById(id).getStatut());
    }

    
    
    
    /**
     * create a new Paiement.
     * @param request the DTO containing the information for the new Paiement.
     * @return the dto of the created Paiement entity.
     */
    @PostMapping
    public ResponseEntity<PaiementResponseDto> createPaiement(
        @RequestBody final PaiementCreateRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(paiementService.createPaiement(request));
    }


}
