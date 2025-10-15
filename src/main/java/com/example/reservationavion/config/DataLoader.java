package com.example.reservationavion.config;

import com.example.reservationavion.entities.Reservation;
import com.example.reservationavion.entities.Vol;
import com.example.reservationavion.repositories.ReservationRepository;
import com.example.reservationavion.repositories.VolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private VolRepository volRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== CHARGEMENT DES DONNÉES ===");
        
        // Chargement des vols
        if (volRepository.count() == 0) {
            Vol vol1 = new Vol();
            vol1.setDestination("Paris");
            vol1.setDepart("Lyon");
            vol1.setDateDepart("2024-01-15");
            vol1.setPlacesDisponibles(150);

            Vol vol2 = new Vol();
            vol2.setDestination("New York");
            vol2.setDepart("Paris");
            vol2.setDateDepart("2024-01-20");
            vol2.setPlacesDisponibles(200);

            Vol vol3 = new Vol();
            vol3.setDestination("Londres");
            vol3.setDepart("Marseille");
            vol3.setDateDepart("2024-01-25");
            vol3.setPlacesDisponibles(120);

            volRepository.save(vol1);
            volRepository.save(vol2);
            volRepository.save(vol3);

            System.out.println("✅ 3 vols créés");

            // Création de réservations de test
            Reservation res1 = new Reservation();
            res1.setNomClient("Jean Dupont");
            res1.setNombrePlaces(2);
            res1.setVol(vol1);

            Reservation res2 = new Reservation();
            res2.setNomClient("Marie Martin");
            res2.setNombrePlaces(1);
            res2.setVol(vol1);

            reservationRepository.save(res1);
            reservationRepository.save(res2);

            // Mettre à jour les places disponibles
            vol1.setPlacesDisponibles(vol1.getPlacesDisponibles() - 3);
            volRepository.save(vol1);

            System.out.println("✅ 2 réservations créées");
        }
        
        System.out.println("Total vols: " + volRepository.count());
        System.out.println("Total réservations: " + reservationRepository.count());
    }
}