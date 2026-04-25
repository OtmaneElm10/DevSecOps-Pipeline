package com.eventapp.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.eventapp.exception.PaiementException.InvalidPaymentException;
import com.eventapp.exception.PaiementException.PaiementNotFoundException;
import com.eventapp.exception.ReservationException.ReservationNotFoundException;
import com.eventapp.model.dto.PaiementCreateRequestDto;
import com.eventapp.model.dto.PaiementResponseDto;
import com.eventapp.model.entities.Paiement;
import com.eventapp.model.entities.Reservation;
import com.eventapp.repositories.PaiementRepository;
import com.eventapp.repositories.ReservationRepository;

@ExtendWith(MockitoExtension.class)
class PaiementServiceTest {

    @Mock
    private PaiementRepository paiementRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private PaiementService paiementService;

    private Reservation buildReservation(final Long id, final String statut,
            final double montant) {
        Reservation r = new Reservation();
        r.setId(id);
        r.setStatut(statut);
        r.setMontantAttendu(montant);
        return r;
    }

    private Paiement buildPaiement(final Long id, final Reservation reservation) {
        Paiement p = new Paiement();
        p.setId(id);
        p.setReservation(reservation);
        p.setMontant(reservation.getMontantAttendu());
        p.setStatut("PAYE");
        p.setDatePaiement(LocalDate.now());
        return p;
    }

    @Test
    void getAllPaiementsShouldReturnEmptyListWhenNoPaiements() {
        when(paiementRepository.findAll()).thenReturn(List.of());

        List<PaiementResponseDto> result = paiementService.getAllPaiements();

        assertThat(result).isEmpty();
    }

    @Test
    void getAllPaiementsShouldReturnMappedDtos() {
        Reservation reservation = buildReservation(1L, "EN_ATTENTE", 50.0);
        Paiement paiement = buildPaiement(1L, reservation);
        when(paiementRepository.findAll()).thenReturn(List.of(paiement));

        List<PaiementResponseDto> result = paiementService.getAllPaiements();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getMontant()).isEqualTo(50.0);
        assertThat(result.get(0).getStatut()).isEqualTo("PAYE");
        assertThat(result.get(0).getReservationId()).isEqualTo(1L);
    }

    @Test
    void createPaiementShouldThrowWhenRequestIsNull() {
        assertThatThrownBy(() -> paiementService.createPaiement(null))
                .isInstanceOf(InvalidPaymentException.class)
                .hasMessage("Reservation id is required");
    }

    @Test
    void createPaiementShouldThrowWhenReservationIdIsNull() {
        PaiementCreateRequestDto request = new PaiementCreateRequestDto();
        request.setReservationId(null);

        assertThatThrownBy(() -> paiementService.createPaiement(request))
                .isInstanceOf(InvalidPaymentException.class)
                .hasMessage("Reservation id is required");
    }

    @Test
    void createPaiementShouldThrowWhenReservationNotFound() {
        PaiementCreateRequestDto request = new PaiementCreateRequestDto();
        request.setReservationId(99L);
        when(reservationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paiementService.createPaiement(request))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    void createPaiementShouldThrowWhenReservationAlreadyPaid() {
        Reservation reservation = buildReservation(1L, "PAYEE", 50.0);
        PaiementCreateRequestDto request = new PaiementCreateRequestDto();
        request.setReservationId(1L);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        assertThatThrownBy(() -> paiementService.createPaiement(request))
                .isInstanceOf(InvalidPaymentException.class)
                .hasMessage("This reservation is already paid");
    }

    @Test
    void createPaiementShouldSaveAndReturnDto() {
        Reservation reservation = buildReservation(1L, "EN_ATTENTE", 75.0);
        Paiement savedPaiement = buildPaiement(1L, reservation);

        PaiementCreateRequestDto request = new PaiementCreateRequestDto();
        request.setReservationId(1L);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(paiementRepository.save(any(Paiement.class))).thenReturn(savedPaiement);

        PaiementResponseDto result = paiementService.createPaiement(request);

        assertThat(result.getMontant()).isEqualTo(75.0);
        assertThat(result.getStatut()).isEqualTo("PAYE");
        assertThat(result.getReservationId()).isEqualTo(1L);
        verify(reservationRepository).save(reservation);
        verify(paiementRepository).save(any(Paiement.class));
    }

    @Test
    void createPaiementShouldMarkReservationAsPaid() {
        Reservation reservation = buildReservation(1L, "EN_ATTENTE", 50.0);
        Paiement savedPaiement = buildPaiement(1L, reservation);

        PaiementCreateRequestDto request = new PaiementCreateRequestDto();
        request.setReservationId(1L);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(paiementRepository.save(any(Paiement.class))).thenReturn(savedPaiement);

        paiementService.createPaiement(request);

        assertThat(reservation.getStatut()).isEqualTo("PAYEE");
    }

    @Test
    void getPaiementByIdShouldReturnDto() {
        Reservation reservation = buildReservation(1L, "PAYEE", 50.0);
        Paiement paiement = buildPaiement(1L, reservation);
        when(paiementRepository.findById(1L)).thenReturn(Optional.of(paiement));

        PaiementResponseDto result = paiementService.getPaiementById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getMontant()).isEqualTo(50.0);
        assertThat(result.getStatut()).isEqualTo("PAYE");
    }

    @Test
    void getPaiementByIdShouldThrowWhenNotFound() {
        when(paiementRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paiementService.getPaiementById(99L))
                .isInstanceOf(PaiementNotFoundException.class);
    }
}