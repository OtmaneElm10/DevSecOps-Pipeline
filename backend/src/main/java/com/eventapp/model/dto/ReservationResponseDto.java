package com.eventapp.model.dto;

import java.time.LocalDate;

/**
 * DTO returned for reservation responses.
 */
public class ReservationResponseDto {

    private Long id;
    private int nbPlaces;
    private String statut;
    private LocalDate dateCreation;
    private double montantAttendu;
    private Long userId;
    private String username;
    private Long eventId;
    private String eventTitle;

    /**
     * Default constructor. Required for JSON deserialization.
     */
    public ReservationResponseDto() {
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

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setEventId(final Long eventId) {
        this.eventId = eventId;
    }

    public void setEventTitle(final String eventTitle) {
        this.eventTitle = eventTitle;
    }
}
