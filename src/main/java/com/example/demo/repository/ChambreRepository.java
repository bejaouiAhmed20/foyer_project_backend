package com.example.demo.repository;

import com.example.demo.entity.Chambre;
import com.example.demo.entity.TypeChambre;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {

    Chambre findByNumeroChambre(Long numeroChambre);

    List<Chambre> findByType(TypeChambre type);

    boolean existsByNumeroChambre(Long numeroChambre);

    List<Chambre> findByBlocIdBloc(Long idBloc);

    List<Chambre> findByBlocNomBloc(String nomBloc);

    long countByTypeAndBlocIdBloc(TypeChambre type, Long idBloc);

    @Query("SELECT c FROM Chambre c WHERE c.bloc.foyer.nomFoyer = :nomFoyer AND c.type = :type " +
           "AND c.idChambre NOT IN (" +
           "  SELECT r.chambre.idChambre FROM Reservation r WHERE r.estValide = true)")
    List<Chambre> findChambresNonReserveesByFoyerAndType(@Param("nomFoyer") String nomFoyer,
                                                          @Param("type") TypeChambre type);
}
