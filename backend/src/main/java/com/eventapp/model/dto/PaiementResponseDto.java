package com.eventapp.model.dto;

import java.time.LocalDate;

import com.eventapp.model.enums.PaiementStatut;

/**

 * DTO returned for payment responses.

 */

public class PaiementResponseDto {

    private Long id;
    private double montant;
    private PaiementStatut statut;
    private LocalDate datePaiement;
    private Long reservationId;

    /**
     * Default constructor.
     */
    public PaiementResponseDto() {

    }

    public Long getId() {
        return id;

    }

    public void setId(final Long id) {
        this.id = id;

    }

    public double getMontant() {
        return montant;

    }

    public void setMontant(final double montant) {
        this.montant = montant;

    }

    public PaiementStatut getStatut() {
        return statut;

    }

    public void setStatut(final PaiementStatut statut) {
        this.statut = statut;

    }

    public LocalDate getDatePaiement() {
        return datePaiement;

    }

    public void setDatePaiement(final LocalDate datePaiement) {
        this.datePaiement = datePaiement;

    }

    public Long getReservationId() {
        return reservationId;

    }

    public void setReservationId(final Long reservationId) {
        this.reservationId = reservationId;

    }

}
