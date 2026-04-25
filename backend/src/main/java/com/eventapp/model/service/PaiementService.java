package com.eventapp.model.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eventapp.exception.PaiementException.InvalidPaymentException;
import com.eventapp.exception.PaiementException.PaiementNotFoundException;
import com.eventapp.exception.ReservationException.ReservationNotFoundException;
import com.eventapp.model.dto.PaiementResponseDto;
import com.eventapp.model.entities.Paiement;
import com.eventapp.model.entities.Reservation;
import com.eventapp.model.enums.PaiementStatut;
import com.eventapp.model.enums.ReservationStatut;
import com.eventapp.repositories.PaiementRepository;
import com.eventapp.repositories.ReservationRepository;

import jakarta.transaction.Transactional;


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
    public List<PaiementResponseDto> getAllPaiements() {
        return paiementRepository.findAll()
            .stream()
            .map(this::toDto)
            .toList();
    }

    /**
     * get all payements for a given user ID.
     * @param userId the ID of the user whose payements are to be retrieved.
     * @return a list of all Paiement entities for the given user ID.
     */
    public List<PaiementResponseDto> getPaiementsByUserId(final Long userId) {
        return paiementRepository.findByReservationUserId(userId)
            .stream()
            .map(this::toDto)
            .toList();
    }




    /**
    * pay a reservation by creating a Paiement entity.
    *
    * @param reservationId the ID of the reservation to be paid.
    * @return created paiement response dto
    */
    @Transactional
    public PaiementResponseDto payReservation(final Long reservationId) {

        if (reservationId == null) {
            throw new InvalidPaymentException("Reservation id is required");
        }

        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(ReservationNotFoundException::new);

        if (ReservationStatut.PAYEE.equals(reservation.getStatut())) {
            throw new InvalidPaymentException("This reservation is already paid");
        }

        if (ReservationStatut.ANNULEE.equals(reservation.getStatut())) {
            throw new InvalidPaymentException("Cannot pay for a cancelled reservation");
        }

        Paiement paiement = paiementRepository.findByReservationId(reservationId)
            .orElseThrow(() -> new InvalidPaymentException(
                "No payment found for this reservation"
            ));

        if (PaiementStatut.EFFECTUE.equals(paiement.getStatut())) {
            throw new InvalidPaymentException("Payment already done");
        }

        paiement.setStatut(PaiementStatut.EFFECTUE);
        paiement.setDatePaiement(LocalDate.now());
        reservation.setStatut(ReservationStatut.PAYEE);

        return toDto(paiement);
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

    
    /**
     * create a pending Paiement for a given Reservation.
     * @param reservation the Reservation for which the pending Paiement is to be created.
     */
    public void createPendingPaiement(final Reservation reservation) {
        Paiement paiement = new Paiement();

        paiement.setReservation(reservation);
        paiement.setMontant(reservation.getMontantAttendu());
        paiement.setStatut(PaiementStatut.EN_ATTENTE);
        paiement.setDatePaiement(null);

        paiementRepository.save(paiement);
    }

    
    /**
     * Cancel a pending Paiement for a given reservation ID.
     * @param reservationId the ID of the reservation 
     * for which the pending Paiement is to be cancelled.
     */
    public void cancelPendingPaiement(final Long reservationId) {
        Paiement paiement = paiementRepository.findByReservationId(reservationId)
            .orElseThrow(() -> new InvalidPaymentException(
                    "No payment found for this reservation"));

        if (PaiementStatut.EFFECTUE.equals(paiement.getStatut())) {
            throw new InvalidPaymentException("Cannot cancel an already completed payment");
        }

        paiement.setStatut(PaiementStatut.ANNULE);

    }


}
