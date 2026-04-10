package com.example.demo.serviceImpl;


import com.example.demo.entity.Universite;
import com.example.demo.repository.UniversiteRepository;
import com.example.demo.service.UniversiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversiteServiceImpl implements UniversiteService {

    private final UniversiteRepository universiteRepository;

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
        return universiteRepository.findById(id).orElse(null);
    }

    @Override
    public Universite updateUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public void deleteUniversite(Long id) {
        universiteRepository.deleteById(id);
    }
}