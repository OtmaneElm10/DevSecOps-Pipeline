
package com.eventapp.model.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Represents a match event in the application.
 * Linked to an Event (EST_MATCH),
 * and to two teams (PARTICIPER_A and PARTICIPER_B).
 */
@Entity
@Table(name = "match_event")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dateMatch;

    // integer plutot que int: si un match n'as pas encore eu lieu , alors le score est
    // null - null  et pas 0-0 (qui peut preter à confusion)
    private Integer scoreA;
    private Integer scoreB;

    @Column(nullable = false)
    private String statut;

    /** PARTICIPER_A : equipe jouant en tant qu equipe A. */
    @ManyToOne
    @JoinColumn(name = "id_equipe_a", nullable = false)
    private Equipe equipeA;

    /** PARTICIPER_B : equipe jouant en tant qu equipe B. */
    @ManyToOne
    @JoinColumn(name = "id_equipe_b", nullable = false)
    private Equipe equipeB;

    /** EST_MATCH : evenement associe a ce match. */
    @OneToOne
    @JoinColumn(name = "id_evenement", nullable = false, unique = true)
    private Event event;

    /**
     * Default constructor.
     */
    public Match() {
    }

    /**
     * Constructs a Match.
     *
     * @param dateMatch the match date
     * @param scoreA    the score of team A
     * @param scoreB    the score of team B
     * @param statut    the match status
     * @param equipeA   team A
     * @param equipeB   team B
     * @param event the associated event
     */
    public Match(final LocalDate dateMatch, final Integer scoreA, final Integer scoreB,
                 final String statut, final Equipe equipeA,
                 final Equipe equipeB, final Event event) {
        this.dateMatch = dateMatch;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.statut = statut;
        this.equipeA = equipeA;
        this.equipeB = equipeB;
        this.event = event;
    }

    public Long getId() { 
        return id; }

    public LocalDate getDateMatch() { 
        return dateMatch; }

    public Integer getScoreA() { 
        return scoreA; }

    public Integer getScoreB() { 
        return scoreB; }

    public String getStatut() { 
        return statut; }

    public Equipe getEquipeA() { 
        return equipeA; }

    public Equipe getEquipeB() { 
        return equipeB; }

    public Event getEvent() { 
        return event; }

    public void setId(final Long id) { 
        this.id = id; }

    public void setDateMatch(final LocalDate dateMatch) { 
        this.dateMatch = dateMatch; }

    public void setScoreA(final Integer scoreA) { 
        this.scoreA = scoreA; }

    public void setScoreB(final Integer scoreB) { 
        this.scoreB = scoreB; }

    public void setStatut(final String statut) { 
        this.statut = statut; }

    public void setEquipeA(final Equipe equipeA) { 
        this.equipeA = equipeA; }

    public void setEquipeB(final Equipe equipeB) { 
        this.equipeB = equipeB; }

    public void setEvent(final Event event) { 
        this.event = event; }
}

