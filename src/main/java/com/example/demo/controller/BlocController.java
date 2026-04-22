package com.example.demo.controller;

import com.example.demo.entity.Bloc;
import com.example.demo.service.BlocService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bloc")
@RequiredArgsConstructor
public class BlocController {

    private final BlocService blocService;

    @PostMapping
    public Bloc addBloc(@RequestBody Bloc b) {
        return blocService.addBloc(b);
    }

    @GetMapping
    public List<Bloc> getAllBlocs() {
        return blocService.getAllBlocs();
    }

    @GetMapping("/{id}")
    public Bloc getById(@PathVariable Long id) {
        return blocService.getBlocById(id);
    }

    @PutMapping
    public Bloc updateBloc(@RequestBody Bloc b) {
        return blocService.updateBloc(b);
    }

    @DeleteMapping("/{id}")
    public void deleteBloc(@PathVariable Long id) {
        blocService.deleteBloc(id);
    }

    @PutMapping("/{blocId}/foyer/{foyerId}")
    public Bloc assignBlocToFoyer(@PathVariable Long blocId, @PathVariable Long foyerId) {
        return blocService.assignBlocToFoyer(blocId, foyerId);
    }
}
