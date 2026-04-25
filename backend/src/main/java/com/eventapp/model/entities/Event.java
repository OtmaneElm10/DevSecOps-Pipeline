package com.eventapp.model.entities;

import java.time.LocalDate;

import com.eventapp.model.enums.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents an event in the application.
 */
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String lieu;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    private int capaciteMax;

    private double prix;

    @Column(columnDefinition = "integer default 0")
    private int nbInscrits = 0;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType type;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User createdBy;

    /**
     * Deafult constructor.
     */
    public Event() {
    }

    
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLieu() {
        return lieu;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public double getPrix() {
        return prix;
    }

    public EventType getType() {
        return type;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setLieu(final String lieu) {
        this.lieu = lieu;
    }

    public void setDateDebut(final LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(final LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public void setCapaciteMax(final int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public void setPrix(final double prix) {
        this.prix = prix;
    }

    public void setType(final EventType type) {
        this.type = type;
    }

    public void setCreatedBy(final User createdBy) {
        this.createdBy = createdBy;
    }

    public int getNbInscrits() {
        return nbInscrits;
    }

    public void setNbInscrits(final int nbInscrits) {
        this.nbInscrits = nbInscrits;
    }
}


