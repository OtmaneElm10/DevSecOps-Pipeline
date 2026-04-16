package com.eventapp.model.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a reservation in the application.
 */
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int nbPlaces;

    @Column(nullable = false)
    private String statut;

    @Column(nullable = false)
    private LocalDate dateCreation;

    private double montantAttendu;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_event", nullable = false)
    private Event event;

    /**
     * Default constructor.
     */
    public Reservation() {
    }

    /**
     * Constructs a Reservation.
     *
     * @param nbPlaces the number of places reserved
     * @param statut the reservation status
     * @param dateCreation the creation date
     * @param montantAttendu the expected amount
     */
    public Reservation(final int nbPlaces, final String statut,
                       final LocalDate dateCreation, final double montantAttendu) {
        this.nbPlaces = nbPlaces;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.montantAttendu = montantAttendu;
    }

    public Long getId() {
        return id;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public String getStatut() {
        return statut;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public double getMontantAttendu() {
        return montantAttendu;
    }

    public User getUser() {
        return user;
    }

    public Event getEvent() {
        return event;
    }

    public void setId(final Long idReservation) {
        this.id = idReservation;
    }

    public void setNbPlaces(final int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public void setStatut(final String statut) {
        this.statut = statut;
    }

    public void setDateCreation(final LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setMontantAttendu(final double montantAttendu) {
        this.montantAttendu = montantAttendu;
    }

    public void setUser(final User utilisateur) {
        this.user = utilisateur;
    }

    public void setEvent(final Event evenement) {
        this.event = evenement;
    }
}
