package com.example.demo.serviceImpl;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.entity.Chambre;
import com.example.demo.entity.Etudiant;
import com.example.demo.entity.Reservation;
import com.example.demo.repository.ChambreRepository;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.service.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ChambreRepository chambreRepository;
    private final EtudiantRepository etudiantRepository;

    @Override
    public Reservation addReservation(Reservation r) {
        // Existing impl or simple save; enhance if needed
        if (r.getChambre() == null || !reservationRepository.existsByChambreIdChambreAndEstValideTrue(r.getChambre().getIdChambre())) {
            return reservationRepository.save(r);
        }
        throw new IllegalStateException("Chambre already reserved");
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll(); // Now with EntityGraph
    }

    @Override
    public Reservation getReservationById(String id) {
        return reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reservation not found: " + id));
    }

    @Override
    public Reservation updateReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    @Override
    public void deleteReservation(String id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public boolean isChambreAvailable(Long chambreId) {
        return !reservationRepository.existsByChambreIdChambreAndEstValideTrue(chambreId);
    }

    @Override
    public Reservation validateReservation(String id) {
        Reservation r = getReservationById(id);
        r.setEstValide(true);
        return reservationRepository.save(r);
    }

    @Override
    public Reservation cancelReservation(String id) {
        Reservation r = getReservationById(id);
        r.setEstValide(false);
        return reservationRepository.save(r);
    }

    @Override
    public ReservationDTO convertToDTO(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setIdReservation(reservation.getIdReservation());
        dto.setAnneeUniversitaire(reservation.getAnneeUniversitaire());
        dto.setEstValide(reservation.isEstValide());
        dto.setChambreId(reservation.getChambre().getIdChambre());
        dto.setEtudiantIds(reservation.getEtudiants().stream().map(Etudiant::getIdEtudiant).collect(Collectors.toSet()));
        if (reservation.getChambre() != null) {
            dto.setNumeroChambre(reservation.getChambre().getNumeroChambre().toString());
            if (reservation.getChambre().getBloc() != null) {
                dto.setNomBloc(reservation.getChambre().getBloc().getNomBloc());
            }
        }
        if (!reservation.getEtudiants().isEmpty()) {
            String noms = reservation.getEtudiants().stream()
                .map(e -> e.getNomEt() + " " + e.getPrenomEt())
                .collect(Collectors.joining(", "));
            dto.setNomEtudiant(noms);
        }
        return dto;
    }

    @Override
    public List<ReservationDTO> getAllReservationDTOs() {
        // To avoid N+1, fetch with graph (repo method needed? use findAll for now)
        return getAllReservations().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Reservation ajouterReservationEtAssignerAChambreEtAEtudiant(Long numChambre, String cin) {
        Etudiant etudiant = etudiantRepository.findByCin(Long.parseLong(cin));
        if (etudiant == null) throw new EntityNotFoundException("Etudiant not found with cin: " + cin);

        Chambre chambre = chambreRepository.findByNumeroChambre(numChambre);
        if (chambre == null) throw new EntityNotFoundException("Chambre not found with numero: " + numChambre);

        long activeReservations = chambre.getReservations().stream().filter(Reservation::isEstValide).count();
        if (activeReservations >= chambre.getType().getPlaces())
            throw new IllegalStateException("Chambre is full");

        LocalDate now = LocalDate.now();
        int startYear = now.getMonthValue() >= 9 ? now.getYear() : now.getYear() - 1;
        String annee = startYear + "/" + (startYear + 1);
        String nomBloc = chambre.getBloc() != null ? chambre.getBloc().getNomBloc() : "NoBloc";
        String idReservation = annee + "-" + nomBloc + "-" + numChambre + "-" + cin;

        if (reservationRepository.existsById(idReservation))
            throw new IllegalStateException("Reservation already exists: " + idReservation);

        Reservation reservation = new Reservation();
        reservation.setIdReservation(idReservation);
        reservation.setAnneeUniversitaire(LocalDate.of(startYear, 9, 1));
        reservation.setEstValide(true);
        reservation.setChambre(chambre);
        reservation.setEtudiants(new java.util.HashSet<>(java.util.Set.of(etudiant)));
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation annulerReservation(long cinEtudiant) {
        Reservation reservation = reservationRepository.findByEtudiantsCin(cinEtudiant)
                .stream().filter(Reservation::isEstValide).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No active reservation found for cin: " + cinEtudiant));
        reservation.setEstValide(false);
        return reservationRepository.save(reservation);
    }

    @Override
    public long getReservationParAnneeUniversitaire(LocalDate debutAnnee, LocalDate finAnnee) {
        return reservationRepository.countByAnneeUniversitaireBetween(debutAnnee, finAnnee);
    }

    @Override
    public Reservation createReservation(ReservationDTO dto) {
        if (dto == null || dto.getChambreId() == null || dto.getEtudiantIds() == null || dto.getEtudiantIds().isEmpty() || dto.getAnneeUniversitaire() == null) {
            throw new IllegalArgumentException("DTO missing required fields");
        }

        if (reservationRepository.existsByChambreIdChambreAndEstValideTrue(dto.getChambreId())) {
            throw new IllegalStateException("Chambre is already reserved for the period");
        }

        Chambre chambre = chambreRepository.findById(dto.getChambreId())
            .orElseThrow(() -> new EntityNotFoundException("Chambre not found with id: " + dto.getChambreId()));

        Set<Etudiant> etudiants = dto.getEtudiantIds().stream()
            .map(id -> etudiantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Etudiant not found with id: " + id)))
            .collect(Collectors.toSet());

        if (etudiants.size() > chambre.getType().getPlaces()) {
            throw new IllegalArgumentException("Number of students (" + etudiants.size() + ") exceeds room capacity (" + chambre.getType().getPlaces() + ")");
        }

        // Generate unique Smart ID
        String year = String.valueOf(dto.getAnneeUniversitaire().getYear());
        String baseId = "R" + chambre.getNumeroChambre() + "-" + year;
        String idReservation = baseId;
        int suffix = 1;
        while (reservationRepository.existsById(idReservation)) {
            idReservation = baseId + "-" + suffix++;
        }

        Reservation reservation = new Reservation(idReservation, dto.getAnneeUniversitaire(), dto.isEstValide(), chambre, etudiants);
        return reservationRepository.save(reservation);
    }
}
