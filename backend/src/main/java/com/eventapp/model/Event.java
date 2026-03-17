package com.eventapp.model;

import java.sql.Date;

public class Event {

    private Long id;

    private String title;
    private String description;
    private String lieu;
    private Date dateDebut;
    private Date dateFin;
    private int capaciteMax;
    private float prix;
    // TODO : Ajouter statut evenement

    public Event() {}


    public Event(String title, String description, String lieu, Date dateDebut, Date dateFin, int capaciteMax, float prix) {
        this.title = title;
        this.description = description;
        this.lieu = lieu;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.capaciteMax = capaciteMax;
        this.prix = prix;
    }

    public Date getDateFin() { return dateFin; }

    public int getCapaciteMax() { return capaciteMax; }

    public float getPrix() { return prix; }

    public Date getDateDebut() { return dateDebut; }

    public String getLieu() { return lieu; }

    public Long getId() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }
}