package com.example.demo.controller;

import com.example.demo.entity.Etudiant;
import com.example.demo.entity.Reservation;
import com.example.demo.service.EtudiantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/etudiant")
@RequiredArgsConstructor
public class EtudiantController {

    private final EtudiantService etudiantService;

    @PostMapping
    public Etudiant addEtudiant(@RequestBody Etudiant e) {
        return etudiantService.addEtudiant(e);
    }

    @GetMapping
    public List<Etudiant> getAllEtudiants() {
        return etudiantService.getAllEtudiants();
    }

    @GetMapping("/{id}")
    public Etudiant getById(@PathVariable Long id) {
        return etudiantService.getEtudiantById(id);
    }

    @PutMapping
    public Etudiant updateEtudiant(@RequestBody Etudiant e) {
        return etudiantService.updateEtudiant(e);
    }

    @GetMapping("/{id}/reservations")
    public Set<Reservation> getReservationHistory(@PathVariable Long id) {
        Etudiant etudiant = etudiantService.getEtudiantById(id);
        if (etudiant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Étudiant introuvable.");
        }
        return etudiant.getReservations();
    }

    @DeleteMapping("/{id}")
    public void deleteEtudiant(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
    }
}
