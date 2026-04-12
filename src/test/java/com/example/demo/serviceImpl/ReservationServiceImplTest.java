package com.example.demo.serviceImpl;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock private ReservationRepository reservationRepository;
    @Mock private ChambreRepository chambreRepository;
    @Mock private EtudiantRepository etudiantRepository;

    @InjectMocks private ReservationServiceImpl service;

    @Test
    void createReservation_Success() {
        // Arrange
        Long chambreId = 1L;
        Set<Long> etudiantIds = Set.of(1L, 2L);
        LocalDate annee = LocalDate.of(2024, 9, 1);
        ReservationDTO dto = new ReservationDTO();
        dto.setChambreId(chambreId);
        dto.setEtudiantIds(etudiantIds);
        dto.setAnneeUniversitaire(annee);
        dto.setEstValide(true);

        Chambre chambre = new Chambre();
        chambre.setIdChambre(chambreId);
        chambre.setNumeroChambre(101L);
        chambre.setType(TypeChambre.DOUBLE);
        when(chambreRepository.findById(chambreId)).thenReturn(Optional.of(chambre)); 

        Etudiant e1 = new Etudiant(); e1.setIdEtudiant(1L);
        Etudiant e2 = new Etudiant(); e2.setIdEtudiant(2L);
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(e1));
        when(etudiantRepository.findById(2L)).thenReturn(Optional.of(e2));

        when(reservationRepository.existsByChambreIdChambreAndEstValideTrue(chambreId)).thenReturn(false);
        when(reservationRepository.existsById(anyString())).thenReturn(false);

        Reservation saved = new Reservation();
        when(reservationRepository.save(any(Reservation.class))).thenReturn(saved);

        // Act
        Reservation result = service.createReservation(dto);

        // Assert
        assertNotNull(result);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void createReservation_ChambreAlreadyReserved() {
        // Arrange
        Long chambreId = 1L;
        ReservationDTO dto = new ReservationDTO();
        dto.setChambreId(chambreId);
        dto.setEtudiantIds(Set.of(1L));
        dto.setAnneeUniversitaire(LocalDate.now());
        when(reservationRepository.existsByChambreIdChambreAndEstValideTrue(chambreId)).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> service.createReservation(dto));
    }

    @Test
    void createReservation_CapacityExceeded() {
        // Arrange
        Long chambreId = 1L;
        ReservationDTO dto = new ReservationDTO();
        dto.setChambreId(chambreId);
        dto.setEtudiantIds(Set.of(1L,2L,3L)); // 3 > 2
        dto.setAnneeUniversitaire(LocalDate.now());

        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101L);
        chambre.setType(TypeChambre.DOUBLE);
        when(chambreRepository.findById(chambreId)).thenReturn(Optional.of(chambre)); 

        Etudiant e = new Etudiant(); e.setIdEtudiant(1L);
        when(etudiantRepository.findById(anyLong())).thenReturn(Optional.of(e));

        when(reservationRepository.existsByChambreIdChambreAndEstValideTrue(chambreId)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.createReservation(dto));
    }

    @Test
    void createReservation_EntityNotFound() {
        // Arrange
        Long chambreId = 1L;
        ReservationDTO dto = new ReservationDTO();
        dto.setChambreId(chambreId);
        dto.setEtudiantIds(Set.of(999L));
        dto.setAnneeUniversitaire(LocalDate.now());

        when(chambreRepository.findById(chambreId)).thenReturn(Optional.of(new Chambre()));
        when(etudiantRepository.findById(999L)).thenReturn(Optional.empty());
        when(reservationRepository.existsByChambreIdChambreAndEstValideTrue(chambreId)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> service.createReservation(dto));
    }

    @Test
    void createReservation_IdCollision() {
        // Arrange
        Long chambreId = 1L;
        ReservationDTO dto = new ReservationDTO();
        dto.setChambreId(chambreId);
        dto.setEtudiantIds(Set.of(1L));
        dto.setAnneeUniversitaire(LocalDate.of(2024, 9, 1));

        Chambre chambre = new Chambre(); chambre.setNumeroChambre(101L); chambre.setType(TypeChambre.SIMPLE);
        when(chambreRepository.findById(chambreId)).thenReturn(Optional.of(chambre)); 

        Etudiant e = new Etudiant(); e.setIdEtudiant(1L);
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(e));

        when(reservationRepository.existsByChambreIdChambreAndEstValideTrue(chambreId)).thenReturn(false);
        when(reservationRepository.existsById("R101-2024")).thenReturn(true);
        when(reservationRepository.existsById("R101-2024-1")).thenReturn(false);

        // Act
        service.createReservation(dto);

        // Assert suffix used
        verify(reservationRepository).existsById("R101-2024");
        verify(reservationRepository).existsById("R101-2024-1");
    }
}
