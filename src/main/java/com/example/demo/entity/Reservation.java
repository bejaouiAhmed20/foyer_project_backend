package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Reservation {

    @Id
    private String idReservation;

    private LocalDate anneeUniversitaire;

    private Boolean estValide;

    @ManyToOne
    @JsonIgnore
    private Chambre chambre;
    @ManyToMany
    private Set<Etudiant> etudiants = new HashSet<>();
}