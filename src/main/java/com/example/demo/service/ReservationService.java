package com.example.demo.service;

import com.example.demo.entity.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation addReservation(Reservation r);

    List<Reservation> getAllReservations();

    Reservation getReservationById(String id);

    Reservation updateReservation(Reservation r);

    void deleteReservation(String id);
}
