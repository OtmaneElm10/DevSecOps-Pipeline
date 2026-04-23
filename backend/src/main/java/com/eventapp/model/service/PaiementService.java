package com.eventapp.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventapp.exception.PaiementException.InvalidPaymentException;
import com.eventapp.exception.PaiementException.PaiementNotFoundException;
import com.eventapp.exception.ReservationException.ReservationNotFoundException;

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
     * get all payements.
     * @return a list of all Paiement entities.
     */
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    /**
     * create a new Paiement.
     * @param paiement the Paiement entity to be created.
     * @return the created Paiement entity.
     */
    public Paiement createPaiement(final Paiement paiement) {
        
        if (paiement == null) {
            throw new InvalidPaymentException("Paiement cannot be null");
        }

        if (paiement.getReservation() == null
            || paiement.getReservation().getId() == null) {

            throw new InvalidPaymentException("Reservation is required");
        }

        Reservation reservation = reservationRepository.findById(paiement.getReservation()
            .getId())
            .orElseThrow(ReservationNotFoundException::new);

        paiement.setReservation(reservation);

        return paiementRepository.save(paiement);

    }

    
    /**
     * get a Paiement by its ID.
     * @param id the ID of the Paiement to be retrieved.
     * @return the Paiement entity with the specified ID.
     */
    public Paiement getPaiementById(final Long id) {
        return paiementRepository.findById(id)
            .orElseThrow(PaiementNotFoundException::new);
    }


}
