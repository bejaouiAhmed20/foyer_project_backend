package com.example.demo.controller;

import com.example.demo.entity.Chambre;
import com.example.demo.service.ChambreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chambre")
@RequiredArgsConstructor
public class ChambreController {

    private final ChambreService chambreService;

    @PostMapping
    public Chambre addChambre(@RequestBody Chambre c) {
        return chambreService.addChambre(c);
    }

    @GetMapping
    public List<Chambre> getAllChambres() {
        return chambreService.getAllChambres();
    }

    @GetMapping("/{id}")
    public Chambre getById(@PathVariable Long id) {
        return chambreService.getChambreById(id);
    }

    @PutMapping
    public Chambre updateChambre(@RequestBody Chambre c) {
        return chambreService.updateChambre(c);
    }

    @DeleteMapping("/{id}")
    public void deleteChambre(@PathVariable Long id) {
        chambreService.deleteChambre(id);
    }
}
