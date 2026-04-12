package com.example.demo.repository;

import com.example.demo.entity.Chambre;
import com.example.demo.entity.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    boolean existsByChambreAndEstValideTrue(Chambre chambre);
    Optional<Reservation> findByChambreAndEstValideTrue(Chambre chambre);

    // id-based convenience methods to avoid extra entity loading
    boolean existsByChambreIdChambreAndEstValideTrue(Long idChambre);
    Optional<Reservation> findByChambreIdChambreAndEstValideTrue(Long idChambre);

    List<Reservation> findByEtudiantsIdEtudiant(Long idEtudiant);

    // N+1 fix for listing
    @EntityGraph(attributePaths = {"chambre", "chambre.bloc", "etudiants"})
    List<Reservation> findAll();
}
