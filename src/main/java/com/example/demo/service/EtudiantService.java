package com.example.demo.service;

import com.example.demo.entity.Etudiant;

import java.util.List;

public interface EtudiantService {

    Etudiant addEtudiant(Etudiant e);

    List<Etudiant> getAllEtudiants();

    Etudiant getEtudiantById(Long id);

    Etudiant updateEtudiant(Etudiant e);

    void deleteEtudiant(Long id);
}

