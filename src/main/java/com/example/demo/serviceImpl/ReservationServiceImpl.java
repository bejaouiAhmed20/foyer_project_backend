package com.example.demo.serviceImpl;

import com.example.demo.entity.Chambre;
import com.example.demo.entity.Etudiant;
import com.example.demo.entity.Reservation;
import com.example.demo.repository.ChambreRepository;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ChambreRepository chambreRepository;
    private final EtudiantRepository etudiantRepository;

    @Override
    @Transactional
    public Reservation addReservation(Reservation r) {
        if (r.getIdReservation() == null || r.getIdReservation().isBlank()) {
            r.setIdReservation(UUID.randomUUID().toString());
        }

        if (r.getChambre() == null || r.getChambre().getIdChambre() == null) {
            throw new IllegalArgumentException("La réservation doit inclure l'id de la chambre.");
        }

        Chambre chambre = chambreRepository.findById(r.getChambre().getIdChambre())
                .orElseThrow(() -> new IllegalArgumentException("Chambre introuvable."));

        if (reservationRepository.existsByChambreAndEstValideTrue(chambre)) {
            throw new IllegalArgumentException("La chambre est déjà réservée et n'est pas disponible.");
        }

        int capacity = chambre.getType().getPlaces();
        Set<Etudiant> resolvedEtudiants = new HashSet<>();

        if (r.getEtudiants() != null) {
            if (r.getEtudiants().size() > capacity) {
                throw new IllegalArgumentException("Le nombre d'étudiants dépasse la capacité de la chambre.");
            }
            for (Etudiant etudiant : r.getEtudiants()) {
                if (etudiant.getIdEtudiant() == null) {
                    throw new IllegalArgumentException("Chaque étudiant doit être référencé par son identifiant.");
                }
                Etudiant resolved = etudiantRepository.findById(etudiant.getIdEtudiant())
                        .orElseThrow(() -> new IllegalArgumentException("Étudiant introuvable: " + etudiant.getIdEtudiant()));
                resolvedEtudiants.add(resolved);
            }
        }

        r.setChambre(chambre);
        r.setEtudiants(resolvedEtudiants);

        return reservationRepository.save(r);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(String id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Reservation updateReservation(Reservation r) {
        if (r.getIdReservation() == null) {
            throw new IllegalArgumentException("L'id de la réservation est obligatoire pour la mise à jour.");
        }

        Reservation existing = reservationRepository.findById(r.getIdReservation())
                .orElseThrow(() -> new IllegalArgumentException("Réservation introuvable."));

        if (r.getChambre() == null || r.getChambre().getIdChambre() == null) {
            throw new IllegalArgumentException("La réservation doit inclure l'id de la chambre.");
        }

        Chambre chambre = chambreRepository.findById(r.getChambre().getIdChambre())
                .orElseThrow(() -> new IllegalArgumentException("Chambre introuvable."));

        reservationRepository.findByChambreAndEstValideTrue(chambre)
                .filter(other -> !other.getIdReservation().equals(r.getIdReservation()))
                .ifPresent(other -> {
                    throw new IllegalArgumentException("La chambre est déjà réservée par une autre réservation valide.");
                });

        int capacity = chambre.getType().getPlaces();
        Set<Etudiant> resolvedEtudiants = new HashSet<>();

        if (r.getEtudiants() != null) {
            if (r.getEtudiants().size() > capacity) {
                throw new IllegalArgumentException("Le nombre d'étudiants dépasse la capacité de la chambre.");
            }
            for (Etudiant etudiant : r.getEtudiants()) {
                if (etudiant.getIdEtudiant() == null) {
                    throw new IllegalArgumentException("Chaque étudiant doit être référencé par son identifiant.");
                }
                Etudiant resolved = etudiantRepository.findById(etudiant.getIdEtudiant())
                        .orElseThrow(() -> new IllegalArgumentException("Étudiant introuvable: " + etudiant.getIdEtudiant()));
                resolvedEtudiants.add(resolved);
            }
        }

        existing.setAnneeUniversitaire(r.getAnneeUniversitaire());
        existing.setEstValide(r.getEstValide());
        existing.setChambre(chambre);
        existing.setEtudiants(resolvedEtudiants);

        return reservationRepository.save(existing);
    }

    @Override
    public void deleteReservation(String id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public boolean isChambreAvailable(Long chambreId) {
        Chambre chambre = chambreRepository.findById(chambreId)
                .orElseThrow(() -> new IllegalArgumentException("Chambre introuvable."));
        return !reservationRepository.existsByChambreAndEstValideTrue(chambre);
    }

    @Override
    public Reservation validateReservation(String id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation introuvable."));
        reservation.setEstValide(true);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation cancelReservation(String id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation introuvable."));
        reservation.setEstValide(false);
        return reservationRepository.save(reservation);
    }
}