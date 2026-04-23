package com.example.demo.service;

import com.example.demo.entity.Universite;

import java.util.List;

public interface UniversiteService {

    Universite addUniversite(Universite u);

    List<Universite> getAllUniversites();

    Universite getUniversiteById(Long id);

    Universite updateUniversite(Universite u);

    void deleteUniversite(Long id);

    Universite desaffecterFoyerAUniversite(long idUniversite);
}
