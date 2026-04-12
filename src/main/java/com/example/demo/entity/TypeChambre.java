package com.example.demo.entity;

public enum TypeChambre {
    SIMPLE(1),
    DOUBLE(2),
    TRIPLE(3);

    private final int places;

    TypeChambre(int places) {
        this.places = places;
    }

    public int getPlaces() {
        return places;
    }
}
