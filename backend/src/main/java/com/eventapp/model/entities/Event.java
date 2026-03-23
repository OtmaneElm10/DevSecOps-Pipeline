package com.eventapp.model.entities;

import java.sql.Date;

/**
 * Represents an event in the application.
 */
public class Event {

    private Long id;
    private String title;
    private String description;
    private String lieu;
    private Date dateDebut;
    private Date dateFin;
    private int capaciteMax;
    private float prix;

    /**
     * Default constructor.
     */
    public Event() {
    }

    /**
     * Constructs an Event.
     *
     * @param title the title of the event
     * @param description the description of the event
     * @param lieu the location of the event
     * @param dateDebut the start date
     * @param dateFin the end date
     * @param capaciteMax the maximum capacity
     * @param prix the price of the event
     */
    public Event(final String title, final String description, final String lieu,
                 final Date dateDebut, final Date dateFin,
                 final int capaciteMax, final float prix) {
        this.title = title;
        this.description = description;
        this.lieu = lieu;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.capaciteMax = capaciteMax;
        this.prix = prix;
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public float getPrix() {
        return prix;
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

    public void setDateDebut(final Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(final Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setCapaciteMax(final int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public void setPrix(final float prix) {
        this.prix = prix;
    }
}
