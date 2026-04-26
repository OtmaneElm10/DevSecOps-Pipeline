package com.eventapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.eventapp.model.dto.PaiementResponseDto;
import com.eventapp.model.enums.PaiementStatut;
import com.eventapp.model.service.PaiementService;

@ExtendWith(MockitoExtension.class)
class PaiementControllerTest {

    @Mock
    private PaiementService paiementService;

    private PaiementController paiementController;

    @BeforeEach
    void setUp() {
        paiementController = new PaiementController(paiementService);
    }

    private PaiementResponseDto buildDto(final Long id, final Long reservationId,
            final double montant) {
        PaiementResponseDto dto = new PaiementResponseDto();
        dto.setId(id);
        dto.setReservationId(reservationId);
        dto.setMontant(montant);
        dto.setStatut(PaiementStatut.EFFECTUE);
        dto.setDatePaiement(LocalDate.now());
        return dto;
    }

    @Test
    void getAllPaiementsShouldReturn200() {
        when(paiementService.getAllPaiements()).thenReturn(List.of());

        ResponseEntity<List<PaiementResponseDto>> response =
                paiementController.getAllPaiements();

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void getAllPaiementsShouldReturnList() {
        List<PaiementResponseDto> dtos = List.of(
                buildDto(1L, 1L, 50.0),
                buildDto(2L, 2L, 75.0));
        when(paiementService.getAllPaiements()).thenReturn(dtos);

        ResponseEntity<List<PaiementResponseDto>> response =
                paiementController.getAllPaiements();

        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void getAllPaiementsShouldReturnEmptyList() {
        when(paiementService.getAllPaiements()).thenReturn(List.of());

        ResponseEntity<List<PaiementResponseDto>> response =
                paiementController.getAllPaiements();

        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void getPaiementByIdShouldReturn200() {
        when(paiementService.getPaiementById(1L)).thenReturn(buildDto(1L, 1L, 50.0));

        ResponseEntity<PaiementResponseDto> response =
                paiementController.getPaiementById(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void getPaiementByIdShouldReturnCorrectDto() {
        PaiementResponseDto dto = buildDto(1L, 1L, 50.0);
        when(paiementService.getPaiementById(1L)).thenReturn(dto);

        ResponseEntity<PaiementResponseDto> response =
                paiementController.getPaiementById(1L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getMontant()).isEqualTo(50.0);
        verify(paiementService).getPaiementById(1L);
    }

    @Test
    void getPaiementStatusShouldReturn200() {
        when(paiementService.getPaiementById(1L)).thenReturn(buildDto(1L, 1L, 50.0));

        ResponseEntity<PaiementStatut> response =
                paiementController.getPaiementStatus(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void getPaiementStatusShouldReturnEFFECTUE() {
        when(paiementService.getPaiementById(1L)).thenReturn(buildDto(1L, 1L, 50.0));

        ResponseEntity<PaiementStatut> response =
                paiementController.getPaiementStatus(1L);

        assertThat(response.getBody()).isEqualTo(PaiementStatut.EFFECTUE);
        verify(paiementService).getPaiementById(1L);
    }

    @Test
    void getPaiementsByUserShouldReturn200() {
        when(paiementService.getPaiementsByUserId(1L)).thenReturn(List.of());

        ResponseEntity<List<PaiementResponseDto>> response =
                paiementController.getPaiementsByUser(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void getPaiementsByUserShouldReturnUserPaiements() {
        List<PaiementResponseDto> dtos = List.of(buildDto(1L, 1L, 50.0));
        when(paiementService.getPaiementsByUserId(1L)).thenReturn(dtos);

        ResponseEntity<List<PaiementResponseDto>> response =
                paiementController.getPaiementsByUser(1L);

        assertThat(response.getBody()).hasSize(1);
        verify(paiementService).getPaiementsByUserId(1L);
    }

    @Test
    void payReservationShouldReturn200() {
        when(paiementService.payReservation(1L)).thenReturn(buildDto(1L, 1L, 50.0));

        ResponseEntity<PaiementResponseDto> response =
                paiementController.payReservation(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void payReservationShouldReturnCreatedPaiement() {
        PaiementResponseDto dto = buildDto(1L, 1L, 75.0);
        when(paiementService.payReservation(1L)).thenReturn(dto);

        ResponseEntity<PaiementResponseDto> response =
                paiementController.payReservation(1L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMontant()).isEqualTo(75.0);
        assertThat(response.getBody().getStatut()).isEqualTo(PaiementStatut.EFFECTUE);
        verify(paiementService).payReservation(1L);
    }
}