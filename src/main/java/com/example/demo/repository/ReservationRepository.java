package com.example.demo.repository;

import com.example.demo.entity.Chambre;
import com.example.demo.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    boolean existsByChambreAndEstValideTrue(Chambre chambre);
    Optional<Reservation> findByChambreAndEstValideTrue(Chambre chambre);
}
