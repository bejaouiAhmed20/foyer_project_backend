package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Chambre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChambre;

    private Long numeroChambre;

    @Enumerated(EnumType.STRING)
    private TypeChambre type;

    @ManyToOne
    @JsonIgnore
    private Bloc bloc;

    @OneToMany(mappedBy = "chambre")
    private List<Reservation> reservations = new ArrayList<>();
}