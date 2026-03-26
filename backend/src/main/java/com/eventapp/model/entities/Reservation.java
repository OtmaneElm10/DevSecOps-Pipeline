package com.eventapp.model.entities;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

/**
 * Represents a reservation in the application.
 */
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    private int nbPlaces;
    private String statut;
    private Date dateCreation;
    private float montantAttendu;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private User utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_evenement")
    private Event evenement;

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
                       final Date dateCreation, final float montantAttendu) {
        this.nbPlaces = nbPlaces;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.montantAttendu = montantAttendu;
    }

    public Long getIdReservation() {
        return idReservation;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public String getStatut() {
        return statut;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public float getMontantAttendu() {
        return montantAttendu;
    }

    public User getUtilisateur() {
        return utilisateur;
    }

    public Event getEvenement() {
        return evenement;
    }

    public void setIdReservation(final Long idReservation) {
        this.idReservation = idReservation;
    }

    public void setNbPlaces(final int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public void setStatut(final String statut) {
        this.statut = statut;
    }

    public void setDateCreation(final Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setMontantAttendu(final float montantAttendu) {
        this.montantAttendu = montantAttendu;
    }

    public void setUtilisateur(final User utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setEvenement(final Event evenement) {
        this.evenement = evenement;
    }
}