package com.example.reservationavion.repositories;

import com.example.reservationavion.entities.Vol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolRepository extends JpaRepository<Vol, Long> {
}