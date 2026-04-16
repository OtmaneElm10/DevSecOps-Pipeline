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
 * Table pour l'inscription à un tournoi.
 */
@Entity
@Table(name = "inscription_tournoi")
public class InscriptionTournoi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String statut;

    @Column(nullable = false)
    private LocalDate dateInscription; 

    @ManyToOne
    @JoinColumn(name = "id_equipe", nullable = false)
    private Equipe equipe;

    @ManyToOne
    @JoinColumn(name = "id_event", nullable = false)
    private Event event;


    
    /**
     * Default constructor.
     */
    public InscriptionTournoi() { }

    
    
    /**
     * Constructor.
     * @param statut statut de l'inscription
     * @param dateInscription date de l'inscription
     * @param equipe equipe concernée 
     * @param event evenement correspondant 
     */
    public InscriptionTournoi(final String statut, 
        final LocalDate dateInscription, final Equipe equipe, final Event event) {

        this.statut = statut;
        this.dateInscription = dateInscription;
        this.equipe = equipe;
        this.event = event;

    }
    

    public Long getId() {
        return id;
    }

    public String getStatut() {
        return statut;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public Event getEvent() {
        return event;
    }


    public void setId(final Long id) { 
        this.id = id; }

    public void setStatut(final String statut) {
        this.statut = statut;
    }

    public void setDateInscription(final LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public void setEquipe(final Equipe equipe) {
        this.equipe = equipe;
    }

    public void setEvent(final Event event) {
        this.event = event;
    }
}
