package com.example.reservationavion.services;

import com.example.reservationavion.entities.Reservation;
import java.util.List;

public interface ReservationService {
    Reservation saveReservation(Reservation reservation);
    List<Reservation> getAllReservations();
    Reservation getReservationById(Long id);
    void deleteReservation(Long id);
    List<Reservation> getReservationsByVolId(Long volId);
}