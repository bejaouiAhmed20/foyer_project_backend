package com.example.demo.service;

import com.example.demo.entity.Foyer;

import java.util.List;

public interface FoyerService {

    Foyer addFoyer(Foyer f);

    List<Foyer> getAllFoyers();

    Foyer getFoyerById(Long id);

    Foyer updateFoyer(Foyer f);

    void deleteFoyer(Long id);

    Foyer linkUniversite(Long foyerId, Long universiteId);
}