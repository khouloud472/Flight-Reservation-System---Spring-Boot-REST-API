package com.example.reservationavion.services;

import com.example.reservationavion.entities.Reservation;
import com.example.reservationavion.entities.Vol;
import com.example.reservationavion.repositories.ReservationRepository;
import com.example.reservationavion.repositories.VolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private VolRepository volRepository;

    @Override
    public Reservation saveReservation(Reservation reservation) {
        // Vérifier si le vol existe et a assez de places
        Vol vol = reservation.getVol();
        if (vol != null && vol.getId() != null) {
            Vol volFromDb = volRepository.findById(vol.getId()).orElse(null);
            if (volFromDb != null) {
                if (volFromDb.getPlacesDisponibles() >= reservation.getNombrePlaces()) {
                    // Mettre à jour les places disponibles
                    volFromDb.setPlacesDisponibles(volFromDb.getPlacesDisponibles() - reservation.getNombrePlaces());
                    volRepository.save(volFromDb);
                    return reservationRepository.save(reservation);
                } else {
                    throw new RuntimeException("Pas assez de places disponibles sur ce vol");
                }
            }
        }
        throw new RuntimeException("Vol invalide");
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation != null) {
            // Remettre les places disponibles
            Vol vol = reservation.getVol();
            if (vol != null) {
                vol.setPlacesDisponibles(vol.getPlacesDisponibles() + reservation.getNombrePlaces());
                volRepository.save(vol);
            }
            reservationRepository.deleteById(id);
        }
    }

    @Override
    public List<Reservation> getReservationsByVolId(Long volId) {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getVol() != null && r.getVol().getId().equals(volId))
                .toList();
    }
}