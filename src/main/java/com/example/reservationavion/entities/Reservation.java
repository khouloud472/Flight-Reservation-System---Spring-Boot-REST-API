package com.example.reservationavion.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entité JPA représentant une réservation effectuée par un client
 * pour un vol donné.
 *
 * Cette classe sera mappée sur une table en base (table RESERVATION).
 */

@Entity
@Data                       // Lombok : génère getters, setters, toString, equals, hashCode
@NoArgsConstructor          // Lombok : constructeur sans arguments (exigé par JPA)
@AllArgsConstructor         // Lombok : constructeur avec tous les champs
public class Reservation {

    /**
     * Identifiant primaire de la réservation.
     * @GeneratedValue(strategy = GenerationType.IDENTITY) :
     * la valeur est générée par la base (auto-increment).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom du client qui a effectué la réservation.
     * Par défaut cela deviendra une colonne NOM_CLIENT dans la table.
     */
    private String nomClient;

    /**
     * Nombre de places réservées pour ce client sur le vol.
     */
    private int nombrePlaces;

    /**
     * Association Many-to-One vers l'entité Vol.
     * - Une réservation concerne un seul vol.
     * - Un vol peut avoir plusieurs réservations.
     *
     * Par défaut :
     *  - le type de fetch pour @ManyToOne est EAGER (la colonne vol est chargée immédiatement),
     *  - la colonne de jointure créée sera "vol_id".
     */
    @ManyToOne
    private Vol vol;

    // H2 est une base de données embarquée (in-memory), ce qui veut dire :
    // - Elle est créée automatiquement au démarrage de l'application si configurée ainsi (jdbc:h2:mem:...)
    // - Elle est supprimée quand l'application s'arrête
    // - Aucun travail manuel de création de la base n'est nécessaire pour le développement
}
