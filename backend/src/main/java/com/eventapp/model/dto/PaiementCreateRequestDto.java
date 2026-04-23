package com.eventapp.model.dto;

/**
 * DTO used to create a payment from a reservation.
 */
public class PaiementCreateRequestDto {

    private Long reservationId;

    /**
     * Default constructor for PaiementCreateRequestDto.
     */
    public PaiementCreateRequestDto() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(final Long reservationId) {
        this.reservationId = reservationId;
    }
}
