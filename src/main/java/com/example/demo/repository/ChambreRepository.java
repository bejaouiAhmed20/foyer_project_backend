package com.example.demo.repository;

import com.example.demo.entity.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {

    // used in advanced services
    Chambre findByNumeroChambre(Long numeroChambre);
}
