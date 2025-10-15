package com.example.reservationavion.services;

import com.example.reservationavion.entities.Vol;

import java.util.List;

public interface VolService {
    Vol saveVol(Vol vol);
    List<Vol> getAllVols();
    Vol getVolById(Long id);
    void deleteVol(Long id);
}
