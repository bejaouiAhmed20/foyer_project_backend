package com.example.demo.serviceImpl;

import com.example.demo.entity.Foyer;
import com.example.demo.repository.FoyerRepository;
import com.example.demo.repository.UniversiteRepository;
import com.example.demo.service.FoyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoyerServiceImpl implements FoyerService {

    private final FoyerRepository foyerRepository;
    private final UniversiteRepository universiteRepository;

    @Override
    public Foyer addFoyer(Foyer f) {
        return foyerRepository.save(f);
    }

    @Override
    public List<Foyer> getAllFoyers() {
        return foyerRepository.findAll();
    }

    @Override
    public Foyer getFoyerById(Long id) {
        return foyerRepository.findById(id).orElse(null);
    }

    @Override
    public Foyer updateFoyer(Foyer f) {
        return foyerRepository.save(f);
    }

    @Override
    public void deleteFoyer(Long id) {
        foyerRepository.deleteById(id);
    }

    @Override
    public Foyer linkUniversite(Long foyerId, Long universiteId) {
        Foyer foyer = foyerRepository.findById(foyerId)
                .orElseThrow(() -> new IllegalArgumentException("Foyer introuvable."));

        return universiteRepository.findById(universiteId)
                .map(universite -> {
                    universite.setFoyer(foyer);
                    foyer.setUniversite(universite);
                    universiteRepository.save(universite);
                    return foyer;
                })
                .orElseThrow(() -> new IllegalArgumentException("Université introuvable."));
    }
}