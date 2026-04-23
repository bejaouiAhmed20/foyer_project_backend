package com.example.demo.service;

import com.example.demo.entity.Chambre;
import com.example.demo.entity.TypeChambre;

import java.util.List;

public interface ChambreService {

    Chambre addChambre(Chambre c);

    List<Chambre> getAllChambres();

    Chambre getChambreById(Long id);

    Chambre updateChambre(Chambre c);

    void deleteChambre(Long id);

    List<Chambre> getChambresParNomBloc(String nomBloc);

    long nbChambreParTypeEtBloc(TypeChambre type, long idBloc);

    List<Chambre> getChambresNonReserveParNomFoyerEtTypeChambre(String nomFoyer, TypeChambre type);
}
