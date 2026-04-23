package com.example.demo.controller;

import com.example.demo.entity.Foyer;
import com.example.demo.service.FoyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/foyer")
@RequiredArgsConstructor
public class FoyerController {

    private final FoyerService foyerService;

    @PostMapping
    public Foyer addFoyer(@RequestBody Foyer f) {
        return foyerService.addFoyer(f);
    }

    @GetMapping
    public List<Foyer> getAllFoyers() {
        return foyerService.getAllFoyers();
    }

    @GetMapping("/{id}")
    public Foyer getById(@PathVariable Long id) {
        return foyerService.getFoyerById(id);
    }

    @PutMapping
    public Foyer updateFoyer(@RequestBody Foyer f) {
        return foyerService.updateFoyer(f);
    }

    @PutMapping("/{foyerId}/universite/{universiteId}")
    public Foyer linkUniversite(@PathVariable Long foyerId, @PathVariable Long universiteId) {
        try {
            return foyerService.linkUniversite(foyerId, universiteId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteFoyer(@PathVariable Long id) {
        foyerService.deleteFoyer(id);
    }

    @PostMapping("/ajouter-et-affecter/{universiteId}")
    public Foyer ajouterFoyerEtAffecterAUniversite(@RequestBody Foyer foyer, @PathVariable long universiteId) {
        return foyerService.ajouterFoyerEtAffecterAUniversite(foyer, universiteId);
    }
}
