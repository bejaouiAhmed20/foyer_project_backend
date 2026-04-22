package com.example.demo.service;

import com.example.demo.entity.Etudiant;
import com.example.demo.entity.Reservation;

import java.util.List;

public interface EtudiantService {

    Etudiant addEtudiant(Etudiant e);

    List<Etudiant> getAllEtudiants();

    Etudiant getEtudiantById(Long id);

    Etudiant updateEtudiant(Etudiant e);

    void deleteEtudiant(Long id);

    List<Reservation> getReservationsByEtudiantId(Long id);
}

