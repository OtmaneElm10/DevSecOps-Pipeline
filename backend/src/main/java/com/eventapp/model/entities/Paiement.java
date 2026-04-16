package com.eventapp.model.entities;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


/**
 * Represents a payment in the application.
 */
@Entity
@Table(name = "paiement")
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double montant;

    @Column(nullable = false)
    private String statut;

    @Column(nullable = false)
    private LocalDate datePaiement;

    @OneToOne
    @JoinColumn(name = "id_reservation", nullable = false, unique = true)
    private Reservation reservation;

    /**
     * Default constructor.
     */
    public Paiement() {
    }

    /**
     * Constructs a Paiement.
     *
     * @param montant the payment amount
     * @param statut the payment status
     * @param datePaiement the payment date
     */
    public Paiement(final double montant, final String statut, final LocalDate datePaiement) {
        this.montant = montant;
        this.statut = statut;
        this.datePaiement = datePaiement;
    }

    public Long getId() {
        return id;
    }

    public double getMontant() {
        return montant;
    }

    public String getStatut() {
        return statut;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setId(final Long idPaiement) {
        this.id = idPaiement;
    }

    public void setMontant(final double montant) {
        this.montant = montant;
    }

    public void setStatut(final String statut) {
        this.statut = statut;
    }

    public void setDatePaiement(final LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public void setReservation(final Reservation reservation) {
        this.reservation = reservation;
    }
}
