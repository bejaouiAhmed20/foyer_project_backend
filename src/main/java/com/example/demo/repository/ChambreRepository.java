package com.example.demo.repository;

import com.example.demo.entity.Chambre;
import com.example.demo.entity.TypeChambre;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {

    Chambre findByNumeroChambre(Long numeroChambre);

    List<Chambre> findByType(TypeChambre type);

    boolean existsByNumeroChambre(Long numeroChambre);

    List<Chambre> findByBlocIdBloc(Long idBloc);
}
