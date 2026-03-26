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
    private String telephone;

    /**
     * Default constructor.
     */
    public Equipe() {
    }

    /**
     * Constructs an Equipe.
     *
     * @param nomEquipe the team name
     * @param telephone the contact phone number
     */
    public Equipe(final String nomEquipe, final String telephone) {
        this.nomEquipe = nomEquipe;
        this.telephone = telephone;
    }

    public Long getId() {
        return id;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setNomEquipe(final String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }
}