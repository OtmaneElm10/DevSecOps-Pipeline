
package com.eventapp.model.entities;

import java.sql.Date;
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

    private Date dateMatch;
    private int scoreA;
    private int scoreB;
    private String statut;

    /** PARTICIPER_A : equipe jouant en tant qu equipe A. */
    @ManyToOne
    @JoinColumn(name = "id_equipe_a")
    private Equipe equipeA;

    /** PARTICIPER_B : equipe jouant en tant qu equipe B. */
    @ManyToOne
    @JoinColumn(name = "id_equipe_b")
    private Equipe equipeB;

    /** EST_MATCH : evenement associe a ce match. */
    @OneToOne
    @JoinColumn(name = "id_evenement")
    private Event evenement;

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
     * @param evenement the associated event
     */
    public Match(final Date dateMatch, final int scoreA, final int scoreB,
                 final String statut, final Equipe equipeA,
                 final Equipe equipeB, final Event evenement) {
        this.dateMatch = dateMatch;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.statut = statut;
        this.equipeA = equipeA;
        this.equipeB = equipeB;
        this.evenement = evenement;
    }

    public Long getId() { return id; }
    public Date getDateMatch() { return dateMatch; }
    public int getScoreA() { return scoreA; }
    public int getScoreB() { return scoreB; }
    public String getStatut() { return statut; }
    public Equipe getEquipeA() { return equipeA; }
    public Equipe getEquipeB() { return equipeB; }
    public Event getEvenement() { return evenement; }

    public void setId(final Long id) { this.id = id; }
    public void setDateMatch(final Date dateMatch) { this.dateMatch = dateMatch; }
    public void setScoreA(final int scoreA) { this.scoreA = scoreA; }
    public void setScoreB(final int scoreB) { this.scoreB = scoreB; }
    public void setStatut(final String statut) { this.statut = statut; }
    public void setEquipeA(final Equipe equipeA) { this.equipeA = equipeA; }
    public void setEquipeB(final Equipe equipeB) { this.equipeB = equipeB; }
    public void setEvenement(final Event evenement) { this.evenement = evenement; }
}