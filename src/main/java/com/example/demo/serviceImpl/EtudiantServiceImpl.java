package com.example.demo.serviceImpl;

import com.example.demo.entity.Etudiant;
import com.example.demo.entity.Reservation;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.service.EtudiantService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EtudiantServiceImpl implements EtudiantService {

    private final EtudiantRepository etudiantRepository;

    @Override
    public Etudiant addEtudiant(Etudiant e) {
        return etudiantRepository.save(e);
    }

    @Override
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @Override
    public Etudiant getEtudiantById(Long id) {
        return etudiantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Etudiant not found with id: " + id));
    }

    @Override
    public Etudiant updateEtudiant(Etudiant e) {
        return etudiantRepository.save(e);
    }

    @Override
    public void deleteEtudiant(Long id) {
        etudiantRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getReservationsByEtudiantId(Long id) {
        if (!etudiantRepository.existsById(id)) {
            throw new EntityNotFoundException("Etudiant not found with id: " + id);
        }
        return etudiantRepository.findReservationsByEtudiantId(id);
    }
}
