package com.example.demo.controller;

import com.example.demo.entity.Universite;
import com.example.demo.service.UniversiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/universite")
@RequiredArgsConstructor
public class UniversiteController {

    private final UniversiteService universiteService;

    // CREATE
    @PostMapping
    public Universite addUniversite(@RequestBody Universite u) {
        return universiteService.addUniversite(u);
    }

    // READ ALL
    @GetMapping
    public List<Universite> getAllUniversites() {
        return universiteService.getAllUniversites();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Universite getById(@PathVariable Long id) {
        return universiteService.getUniversiteById(id);
    }

    // UPDATE
    @PutMapping
    public Universite updateUniversite(@RequestBody Universite u) {
        return universiteService.updateUniversite(u);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteUniversite(@PathVariable Long id) {
        universiteService.deleteUniversite(id);
    }

    @PutMapping("/{id}/desaffecter-foyer")
    public Universite desaffecterFoyer(@PathVariable Long id) {
        return universiteService.desaffecterFoyerAUniversite(id);
    }
}
