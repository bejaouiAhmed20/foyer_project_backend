package com.example.demo.serviceImpl;

import com.example.demo.entity.Foyer;
import com.example.demo.entity.Universite;
import com.example.demo.repository.FoyerRepository;
import com.example.demo.repository.UniversiteRepository;
import com.example.demo.service.UniversiteService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UniversiteServiceImpl implements UniversiteService {

    private final UniversiteRepository universiteRepository;
    private final FoyerRepository foyerRepository;

    @Override
    public Universite addUniversite(Universite u) {

        // Business rule: add foyer with universite
        if (u.getFoyer() != null) {
            u.getFoyer().setUniversite(u);
        }

        return universiteRepository.save(u);
    }

    @Override
    public List<Universite> getAllUniversites() {
        return universiteRepository.findAll();
    }

    @Override
    public Universite getUniversiteById(Long id) {
        return universiteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Universite not found with id: " + id));
    }

    @Override
    public Universite updateUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public void deleteUniversite(Long id) {
        universiteRepository.deleteById(id);
    }

    @Override
    public Universite desaffecterFoyerAUniversite(long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new EntityNotFoundException("Universite not found with id: " + idUniversite));
        Foyer foyer = universite.getFoyer();
        if (foyer != null) {
            foyer.setUniversite(null);
            foyerRepository.save(foyer);
        }
        universite.setFoyer(null);
        return universiteRepository.save(universite);
    }
}
