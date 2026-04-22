package com.example.demo.repository;

import com.example.demo.entity.Etudiant;
import com.example.demo.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    Etudiant findByCin(Long cin);

    boolean existsByCin(Long cin);

    Optional<Etudiant> findById(Long id);

    @Query("SELECT r FROM Reservation r JOIN r.etudiants e WHERE e.idEtudiant = :id")
    List<Reservation> findReservationsByEtudiantId(@Param("id") Long id);
}
