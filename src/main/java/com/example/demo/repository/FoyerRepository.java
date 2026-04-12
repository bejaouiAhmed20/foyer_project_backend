package com.example.demo.repository;

import com.example.demo.entity.Foyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoyerRepository extends JpaRepository<Foyer, Long> {
    Foyer findByNomFoyer(String nomFoyer);
    boolean existsByNomFoyer(String nomFoyer);
}
