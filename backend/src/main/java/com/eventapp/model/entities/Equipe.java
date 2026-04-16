package com.eventapp.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a team in the application.
 */
@Entity
@Table(name = "equipe")
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomEquipe;

    /**
     * Default constructor.
     */
    public Equipe() {
    }

    /**
     * Constructs an Equipe.
     *
     * @param nomEquipe the team name
     */
    public Equipe(final String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public Long getId() {
        return id;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }


    public void setId(final Long id) {
        this.id = id;
    }

    public void setNomEquipe(final String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }
}

