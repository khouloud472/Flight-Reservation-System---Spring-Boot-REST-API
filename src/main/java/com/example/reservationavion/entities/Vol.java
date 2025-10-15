package com.example.reservationavion.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESTINATION")
    private String destination;

    @Column(name = "DEPART")
    private String depart;

    @Column(name = "DATE_DEPART")
    private String dateDepart;

    @Column(name = "PLACES_DISPONIBLES")
    private int placesDisponibles;
}
