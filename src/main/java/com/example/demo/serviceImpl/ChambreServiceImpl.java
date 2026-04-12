package com.example.demo.serviceImpl;

import com.example.demo.entity.Chambre;
import com.example.demo.repository.BlocRepository;
import com.example.demo.repository.ChambreRepository;
import com.example.demo.service.ChambreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChambreServiceImpl implements ChambreService {

    private final ChambreRepository chambreRepository;
    private final BlocRepository blocRepository;

    @Override
    public Chambre addChambre(Chambre c) {
        if (c.getBloc() != null && c.getBloc().getIdBloc() != null) {
            c.setBloc(blocRepository.findById(c.getBloc().getIdBloc()).orElse(null));
        }
        return chambreRepository.save(c);
    }

    @Override
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }

    @Override
    public Chambre getChambreById(Long id) {
        return chambreRepository.findById(id).orElse(null);
    }

    @Override
    public Chambre updateChambre(Chambre c) {
        return chambreRepository.save(c);
    }

    @Override
    public void deleteChambre(Long id) {
        chambreRepository.deleteById(id);
    }
}