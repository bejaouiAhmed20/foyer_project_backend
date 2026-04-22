package com.example.demo.service;

import com.example.demo.entity.Bloc;

import java.util.List;

public interface BlocService {

    Bloc addBloc(Bloc b);

    List<Bloc> getAllBlocs();

    Bloc getBlocById(Long id);

    Bloc updateBloc(Bloc b);

    void deleteBloc(Long id);

    Bloc assignBlocToFoyer(Long blocId, Long foyerId);
}