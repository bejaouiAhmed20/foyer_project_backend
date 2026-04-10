package com.example.demo.serviceImpl;

import com.example.demo.entity.Bloc;
import com.example.demo.repository.BlocRepository;
import com.example.demo.service.BlocService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlocServiceImpl implements BlocService {

    private final BlocRepository blocRepository;

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
        return blocRepository.findById(id).orElse(null);
    }

    @Override
    public Bloc updateBloc(Bloc b) {
        return blocRepository.save(b);
    }

    @Override
    public void deleteBloc(Long id) {
        blocRepository.deleteById(id);
    }
}
