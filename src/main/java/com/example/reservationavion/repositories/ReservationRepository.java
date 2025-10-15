package com.example.reservationavion.repositories;

import com.example.reservationavion.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface Repository pour l'entité Reservation.
 *
 * En étendant JpaRepository<Reservation, Long>, cette interface hérite
 * automatiquement d'un ensemble de méthodes prêtes à l'emploi
 * pour manipuler la table Reservation :
 *  - findAll()        → récupérer toutes les réservations
 *  - findById(Long id)→ récupérer une réservation par son ID
 *  - save(Reservation r) → insérer ou mettre à jour une réservation
 *  - deleteById(Long id) → supprimer une réservation
 * 
 * Grâce à Spring Data JPA :
 *  - Aucune implémentation n'est nécessaire (Spring la génère automatiquement au démarrage)
 *  - Les méthodes peuvent être étendues en ajoutant des requêtes personnalisées,
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Rien à écrire ici pour le moment : toutes les méthodes CRUD sont héritées de JpaRepository.

    // Exemple de méthode personnalisée qu'on pourrait ajouter :
    // List<Reservation> findByNomClient(String nomClient);
}
