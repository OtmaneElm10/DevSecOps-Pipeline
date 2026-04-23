package com.eventapp.model.dto;

import java.time.LocalDate;

/**
 * DTO used to create a payment.
 */
public class PaiementCreateRequestDto {

    private double montant;
    private String statut;
    private LocalDate datePaiement;
    private Long reservationId;

    /**
     * Default constructor.
     */
    public PaiementCreateRequestDto() {
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(final double montant) {
        this.montant = montant;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(final String statut) {
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
