package com.example.demo.controller;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.entity.Reservation;
import com.example.demo.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationDTO dto) {
        Reservation saved = reservationService.createReservation(dto);
        return ResponseEntity.ok(reservationService.convertToDTO(saved));
    }

    @GetMapping
    public List<ReservationDTO> getAllReservations() {
        return reservationService.getAllReservationDTOs();
    }

    @GetMapping("/{id}")
    public ReservationDTO getReservationById(@PathVariable String id) {
        Reservation res = reservationService.getReservationById(id);
        return reservationService.convertToDTO(res);
    }

    @PutMapping
    public ResponseEntity<ReservationDTO> updateReservation(@Valid @RequestBody ReservationDTO dto) {
        // Map DTO to entity for update (or add service.updateReservation(ReservationDTO))
        Reservation existing = reservationService.getReservationById(dto.getIdReservation());
        existing.setAnneeUniversitaire(dto.getAnneeUniversitaire());
        existing.setEstValide(dto.isEstValide());
        // Update chambre/etudiants if changed, but for simplicity update basics
        Reservation updated = reservationService.updateReservation(existing);
        return ResponseEntity.ok(reservationService.convertToDTO(updated));
    }

    @PutMapping("/{id}/validate")
    public ReservationDTO validateReservation(@PathVariable String id) {
        Reservation res = reservationService.validateReservation(id);
        return reservationService.convertToDTO(res);
    }

    @PutMapping("/{id}/cancel")
    public ReservationDTO cancelReservation(@PathVariable String id) {
        Reservation res = reservationService.cancelReservation(id);
        return reservationService.convertToDTO(res);
    }

    @GetMapping("/availability/{chambreId}")
    public boolean isChambreAvailable(@PathVariable Long chambreId) {
        return reservationService.isChambreAvailable(chambreId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/ajouter-et-assigner")
    public Reservation ajouterReservationEtAssigner(@RequestParam Long numChambre, @RequestParam String cin) {
        return reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(numChambre, cin);
    }

    @PutMapping("/annuler/{cin}")
    public Reservation annulerReservation(@PathVariable long cin) {
        return reservationService.annulerReservation(cin);
    }

    @GetMapping("/count")
    public long getReservationParAnnee(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debutAnnee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finAnnee) {
        return reservationService.getReservationParAnneeUniversitaire(debutAnnee, finAnnee);
    }
}
