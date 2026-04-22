package com.example.demo.serviceImpl;

import com.example.demo.entity.Bloc;
import com.example.demo.entity.Foyer;
import com.example.demo.repository.BlocRepository;
import com.example.demo.repository.FoyerRepository;
import com.example.demo.service.BlocService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlocServiceImpl implements BlocService {

    private final BlocRepository blocRepository;
    private final FoyerRepository foyerRepository;

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
}
