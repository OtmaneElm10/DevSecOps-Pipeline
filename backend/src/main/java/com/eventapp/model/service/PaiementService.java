package com.eventapp.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventapp.exception.PaiementException.InvalidPaymentException;
import com.eventapp.exception.PaiementException.PaiementNotFoundException;
import com.eventapp.exception.ReservationException.ReservationNotFoundException;
import com.eventapp.model.dto.PaiementCreateRequestDto;
import com.eventapp.model.dto.PaiementResponseDto;
import com.eventapp.model.entities.Paiement;

import com.eventapp.model.entities.Reservation;

import com.eventapp.repositories.PaiementRepository;

import com.eventapp.repositories.ReservationRepository;


/**
 * Service class for managing Paiement entities.
 */
@Service
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final ReservationRepository reservationRepository;

    /**
     * Constructor for PaiementService.
     * @param paiementRepository repository for managing Paiement entities.
     * @param reservationRepository repository for managing Reservation entities.
     */
    public PaiementService(final PaiementRepository paiementRepository,
                           final ReservationRepository reservationRepository) {
        this.paiementRepository = paiementRepository;
        this.reservationRepository = reservationRepository;
    }

    /**
     * convert a Paiement entity to a PaiementResponseDto.
    * @param paiement the Paiement entity to be converted.
     * @return the converted PaiementResponseDto.
     */
    private PaiementResponseDto toDto(final Paiement paiement) {
        PaiementResponseDto dto = new PaiementResponseDto();
        
        dto.setId(paiement.getId());
        dto.setMontant(paiement.getMontant());
        dto.setStatut(paiement.getStatut());
        dto.setDatePaiement(paiement.getDatePaiement());
        dto.setReservationId(paiement.getReservation().getId());
        return dto;
    }


    /**
     * get all payements.
     * @return a list of all Paiement entities.
     */
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }


    /**
     * create a new Paiement.
     * @param request the DTO containing the information for the new Paiement.
     * @return the created PaiementResponseDto.
     */
    public PaiementResponseDto createPaiement(final PaiementCreateRequestDto request) {
        
        if (request == null) {
            throw new InvalidPaymentException("Paiement cannot be null");
        }

        if (request.getMontant() <= 0) {
            throw new InvalidPaymentException("Montant must be greater than zero");
        }

        if (request.getReservationId() == null) {
            throw new InvalidPaymentException("Reservation is required");
        }

        Reservation reservation = reservationRepository.findById(request.getReservationId())
            .orElseThrow(ReservationNotFoundException::new);

        Paiement paiement = new Paiement();
        paiement.setMontant(request.getMontant());
        paiement.setStatut(request.getStatut());
        paiement.setDatePaiement(request.getDatePaiement());
        paiement.setReservation(reservation);   

        return toDto(paiementRepository.save(paiement));

    }

    
    /**
     * get a Paiement by its ID.
     * @param id the ID of the Paiement to be retrieved.
     * @return the PaiementResponseDto with the specified ID.
     */
    public PaiementResponseDto getPaiementById(final Long id) {
        Paiement paiement = paiementRepository.findById(id)
            .orElseThrow(PaiementNotFoundException::new);
        
        return toDto(paiement);
    }


}
