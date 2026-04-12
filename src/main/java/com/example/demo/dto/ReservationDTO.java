package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

public class ReservationDTO {

    private String idReservation;

    @NotNull(message = "anneeUniversitaire is required")
    private LocalDate anneeUniversitaire;

    private boolean estValide;

    @NotNull(message = "chambreId is required")
    private Long chambreId;

    @NotEmpty(message = "etudiantIds must contain at least one student")
    private Set<Long> etudiantIds;

    // Optional response fields
    private String numeroChambre;
    private String nomBloc;
    private String nomEtudiant;

    public String getIdReservation() { return idReservation; }
    public void setIdReservation(String idReservation) { this.idReservation = idReservation; }

    public LocalDate getAnneeUniversitaire() { return anneeUniversitaire; }
    public void setAnneeUniversitaire(LocalDate anneeUniversitaire) { this.anneeUniversitaire = anneeUniversitaire; }

    public boolean isEstValide() { return estValide; }
    public void setEstValide(boolean estValide) { this.estValide = estValide; }

    public Long getChambreId() { return chambreId; }
    public void setChambreId(Long chambreId) { this.chambreId = chambreId; }

    public Set<Long> getEtudiantIds() { return etudiantIds; }
    public void setEtudiantIds(Set<Long> etudiantIds) { this.etudiantIds = etudiantIds; }

    public String getNumeroChambre() { return numeroChambre; }
    public void setNumeroChambre(String numeroChambre) { this.numeroChambre = numeroChambre; }

    public String getNomBloc() { return nomBloc; }
    public void setNomBloc(String nomBloc) { this.nomBloc = nomBloc; }

    public String getNomEtudiant() { return nomEtudiant; }
    public void setNomEtudiant(String nomEtudiant) { this.nomEtudiant = nomEtudiant; }
}
