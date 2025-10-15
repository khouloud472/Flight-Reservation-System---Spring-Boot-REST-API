package com.example.reservationavion.services;

import com.example.reservationavion.entities.Vol;
import com.example.reservationavion.repositories.VolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// ✅ BIEN AJOUTER @Service
@Service
public class VolServiceImpl implements VolService {

    @Autowired
    private VolRepository volRepository;

    public VolServiceImpl() {
        System.out.println("🎯 VOL SERVICE IMPL CRÉÉ !");
    }

    @Override
    public List<Vol> getAllVols() {
        List<Vol> vols = volRepository.findAll();
        System.out.println("🎯 Service - Vols en base: " + vols.size());
        return vols;
    }

    @Override
    public Vol saveVol(Vol vol) {
        return volRepository.save(vol);
    }

    @Override
    public Vol getVolById(Long id) {
        return volRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteVol(Long id) {
        volRepository.deleteById(id);
    }
}