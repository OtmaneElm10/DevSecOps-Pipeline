package com.eventapp.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

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
import com.eventapp.model.dto.PaiementResponseDto;
import com.eventapp.model.entities.Paiement;
import com.eventapp.model.entities.Reservation;
import com.eventapp.model.enums.PaiementStatut;
import com.eventapp.model.enums.ReservationStatut;
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

    private Reservation buildReservation(final Long id, 
        final ReservationStatut statut, final double montant) {
        Reservation r = new Reservation();
        r.setId(id);
        r.setStatut(statut);
        r.setMontantAttendu(montant);
        return r;
    }

    private Paiement buildPaiement(final Long id,
         final Reservation reservation, final PaiementStatut statut) {
        Paiement p = new Paiement();
        p.setId(id);
        p.setReservation(reservation);
        p.setMontant(reservation.getMontantAttendu());
        p.setStatut(statut);
        p.setDatePaiement(null);
        return p;
    }

    @Test
    void getAllPaiementsShouldReturnEmptyList() {
        when(paiementRepository.findAll()).thenReturn(List.of());

        List<PaiementResponseDto> result = paiementService.getAllPaiements();

        assertThat(result).isEmpty();
    }

    @Test
    void getAllPaiementsShouldReturnDtos() {
        Reservation reservation = buildReservation(1L, 
            ReservationStatut.EN_ATTENTE_DE_PAIEMENT, 50.0);
        Paiement paiement = buildPaiement(1L, reservation, PaiementStatut.EN_ATTENTE);

        when(paiementRepository.findAll()).thenReturn(List.of(paiement));

        List<PaiementResponseDto> result = paiementService.getAllPaiements();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMontant()).isEqualTo(50.0);
        assertThat(result.get(0).getStatut()).isEqualTo(PaiementStatut.EN_ATTENTE);
    }

    @Test
    void payReservationShouldWork() {
        Reservation reservation = buildReservation(1L, 
            ReservationStatut.EN_ATTENTE_DE_PAIEMENT, 50.0);
        Paiement paiement = buildPaiement(1L, reservation, PaiementStatut.EN_ATTENTE);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(paiementRepository.findByReservationId(1L)).thenReturn(Optional.of(paiement));

        PaiementResponseDto result = paiementService.payReservation(1L);

        assertThat(result.getStatut()).isEqualTo(PaiementStatut.EFFECTUE);
        assertThat(reservation.getStatut()).isEqualTo(ReservationStatut.PAYEE);
        assertThat(paiement.getDatePaiement()).isNotNull();
    }

    @Test
    void payReservationShouldThrowIfReservationNotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paiementService.payReservation(1L))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    void payReservationShouldThrowIfAlreadyPaid() {
        Reservation reservation = buildReservation(1L, ReservationStatut.PAYEE, 50.0);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        assertThatThrownBy(() -> paiementService.payReservation(1L))
                .isInstanceOf(InvalidPaymentException.class);
    }

    @Test
    void payReservationShouldThrowIfCancelled() {
        Reservation reservation = buildReservation(1L, ReservationStatut.ANNULEE, 50.0);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        assertThatThrownBy(() -> paiementService.payReservation(1L))
                .isInstanceOf(InvalidPaymentException.class);
    }

    @Test
    void payReservationShouldThrowIfPaymentAlreadyDone() {
        Reservation reservation = buildReservation(1L, 
            ReservationStatut.EN_ATTENTE_DE_PAIEMENT, 50.0);
        Paiement paiement = buildPaiement(1L, reservation, PaiementStatut.EFFECTUE);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(paiementRepository.findByReservationId(1L)).thenReturn(Optional.of(paiement));

        assertThatThrownBy(() -> paiementService.payReservation(1L))
                .isInstanceOf(InvalidPaymentException.class);
    }

    @Test
    void getPaiementByIdShouldReturnDto() {
        Reservation reservation = buildReservation(1L, ReservationStatut.PAYEE, 50.0);
        Paiement paiement = buildPaiement(1L, reservation, PaiementStatut.EFFECTUE);

        when(paiementRepository.findById(1L)).thenReturn(Optional.of(paiement));

        PaiementResponseDto result = paiementService.getPaiementById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStatut()).isEqualTo(PaiementStatut.EFFECTUE);
    }

    @Test
    void getPaiementByIdShouldThrow() {
        when(paiementRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paiementService.getPaiementById(99L))
                .isInstanceOf(PaiementNotFoundException.class);
    }
}
