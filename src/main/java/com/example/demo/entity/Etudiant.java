package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idEtudiant;

    private String nomEt;
    private String prenomEt;

    @Column(unique = true, nullable = false)
    private Long cin;

    private String ecole;

    private LocalDate dateNaissance;

    @ManyToMany
    @JoinTable(
            name = "reservation_etudiants",
            joinColumns = @JoinColumn(name = "reservation_id_reservation"), // This fixes the column mismatch!
            inverseJoinColumns = @JoinColumn(name = "etudiants_id_etudiant")
    )
    private Set<Etudiant> etudiants = new HashSet<>();

}
