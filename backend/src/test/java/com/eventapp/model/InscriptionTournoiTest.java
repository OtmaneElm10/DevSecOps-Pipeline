package com.eventapp.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.Equipe;
import com.eventapp.model.entities.Event;
import com.eventapp.model.entities.InscriptionTournoi;

class InscriptionTournoiTest {

    @Test
    void defaultConstructorShouldCreateEmptyInscription() {
        InscriptionTournoi inscription = new InscriptionTournoi();

        assertThat(inscription.getId()).isNull();
        assertThat(inscription.getStatut()).isNull();
        assertThat(inscription.getDateInscription()).isNull();
        assertThat(inscription.getEquipe()).isNull();
        assertThat(inscription.getEvent()).isNull();
    }

    @Test
    void constructorShouldSetAllFields() {
        LocalDate date = LocalDate.now();
        Equipe equipe = new Equipe("Equipe A");
        Event event = new Event();

        InscriptionTournoi inscription = new InscriptionTournoi(
            "EN_ATTENTE", date, equipe, event);

        assertThat(inscription.getStatut()).isEqualTo("EN_ATTENTE");
        assertThat(inscription.getDateInscription()).isEqualTo(date);
        assertThat(inscription.getEquipe()).isEqualTo(equipe);
        assertThat(inscription.getEvent()).isEqualTo(event);
    }

    @Test
    void settersShouldUpdateFields() {
        InscriptionTournoi inscription = new InscriptionTournoi();
        LocalDate date = LocalDate.of(2024, 6, 15);
        Equipe equipe = new Equipe("Equipe B");
        Event event = new Event();

        inscription.setId(1L);
        inscription.setStatut("CONFIRME");
        inscription.setDateInscription(date);
        inscription.setEquipe(equipe);
        inscription.setEvent(event);

        assertThat(inscription.getId()).isEqualTo(1L);
        assertThat(inscription.getStatut()).isEqualTo("CONFIRME");
        assertThat(inscription.getDateInscription()).isEqualTo(date);
        assertThat(inscription.getEquipe()).isEqualTo(equipe);
        assertThat(inscription.getEvent()).isEqualTo(event);
    }

    @Test
    void statutShouldBeUpdatable() {
        InscriptionTournoi inscription = new InscriptionTournoi();
        inscription.setStatut("EN_ATTENTE");
        assertThat(inscription.getStatut()).isEqualTo("EN_ATTENTE");

        inscription.setStatut("CONFIRME");
        assertThat(inscription.getStatut()).isEqualTo("CONFIRME");

        inscription.setStatut("REFUSE");
        assertThat(inscription.getStatut()).isEqualTo("REFUSE");
    }
}