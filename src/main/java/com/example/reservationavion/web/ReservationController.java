package com.example.reservationavion.web;

import com.example.reservationavion.entities.Reservation;
import com.example.reservationavion.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    public ReservationController() {
        System.out.println("✅ ReservationController créé !");
    }

    // Récupérer toutes les réservations
    @GetMapping
    public List<Reservation> getAllReservations() {
        System.out.println("🎯 GET /api/reservations appelé !");
        return reservationService.getAllReservations();
    }

    // Créer une nouvelle réservation
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        try {
            System.out.println("🎯 POST /api/reservations appelé");
            Reservation savedReservation = reservationService.saveReservation(reservation);
            return ResponseEntity.ok(savedReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Récupérer une réservation par ID
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer une réservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

    // Récupérer les réservations d'un vol spécifique
    @GetMapping("/vol/{volId}")
    public List<Reservation> getReservationsByVolId(@PathVariable Long volId) {
        return reservationService.getReservationsByVolId(volId);
    }

    // Test endpoint
    @GetMapping("/test")
    public String test() {
        return "✅ API Réservations fonctionne ! " + System.currentTimeMillis();
    }
}