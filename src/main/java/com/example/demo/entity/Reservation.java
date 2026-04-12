package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Reservation {

    @Id
    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String idReservation;

    private LocalDate anneeUniversitaire;

    private boolean estValide;

    @ManyToOne
    @JsonIgnoreProperties({"reservations", "bloc"})
    private Chambre chambre;

    @ManyToMany
    @ToString.Exclude
    private Set<Etudiant> etudiants = new HashSet<>();
}
