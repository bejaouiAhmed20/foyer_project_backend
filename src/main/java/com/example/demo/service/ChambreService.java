package com.example.demo.service;

import com.example.demo.entity.Chambre;

import java.util.List;

public interface ChambreService {

    Chambre addChambre(Chambre c);

    List<Chambre> getAllChambres();

    Chambre getChambreById(Long id);

    Chambre updateChambre(Chambre c);

    void deleteChambre(Long id);
}
