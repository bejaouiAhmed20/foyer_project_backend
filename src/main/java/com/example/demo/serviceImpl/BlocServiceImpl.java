package com.example.demo.serviceImpl;

import com.example.demo.entity.Bloc;
import com.example.demo.entity.Chambre;
import com.example.demo.entity.Foyer;
import com.example.demo.repository.BlocRepository;
import com.example.demo.repository.ChambreRepository;
import com.example.demo.repository.FoyerRepository;
import com.example.demo.service.BlocService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BlocServiceImpl implements BlocService {

    private final BlocRepository blocRepository;
    private final FoyerRepository foyerRepository;
    private final ChambreRepository chambreRepository;

    @Override
    public Bloc addBloc(Bloc b) {

        // Business rule: add chambres with bloc
        if (b.getChambres() != null) {
            b.getChambres().forEach(c -> c.setBloc(b));
        }

        return blocRepository.save(b);
    }

    @Override
    public List<Bloc> getAllBlocs() {
        return blocRepository.findAll();
    }

    @Override
    public Bloc getBlocById(Long id) {
        return blocRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bloc not found with id: " + id));
    }

    @Override
    public Bloc updateBloc(Bloc b) {
        return blocRepository.save(b);
    }

    @Override
    public void deleteBloc(Long id) {
        blocRepository.deleteById(id);
    }

    @Override
    public Bloc assignBlocToFoyer(Long blocId, Long foyerId) {
        Bloc bloc = blocRepository.findById(blocId)
                .orElseThrow(() -> new EntityNotFoundException("Bloc not found with id: " + blocId));
        Foyer foyer = foyerRepository.findById(foyerId)
                .orElseThrow(() -> new EntityNotFoundException("Foyer not found with id: " + foyerId));
        bloc.setFoyer(foyer);
        return blocRepository.save(bloc);
    }

    @Override
    public Bloc affecterChambresABloc(List<Long> numChambres, String nomBloc) {
        Bloc bloc = blocRepository.findByNomBloc(nomBloc);
        if (bloc == null) {
            throw new EntityNotFoundException("Bloc not found with name: " + nomBloc);
        }
        for (Long num : numChambres) {
            Chambre chambre = chambreRepository.findByNumeroChambre(num);
            if (chambre == null) {
                throw new EntityNotFoundException("Chambre not found with numeroChambre: " + num);
            }
            chambre.setBloc(bloc);
            chambreRepository.save(chambre);
        }
        return blocRepository.findById(bloc.getIdBloc()).orElseThrow();
    }

    @Override
    public Bloc affecterBlocAFoyer(String nomBloc, String nomFoyer) {
        Bloc bloc = blocRepository.findByNomBloc(nomBloc);
        if (bloc == null) {
            throw new EntityNotFoundException("Bloc not found with name: " + nomBloc);
        }
        Foyer foyer = foyerRepository.findByNomFoyer(nomFoyer);
        if (foyer == null) {
            throw new EntityNotFoundException("Foyer not found with name: " + nomFoyer);
        }
        bloc.setFoyer(foyer);
        return blocRepository.save(bloc);
    }
}
