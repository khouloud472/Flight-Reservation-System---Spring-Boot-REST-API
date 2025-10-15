package com.example.reservationavion.web;

import com.example.reservationavion.entities.Vol;
import com.example.reservationavion.services.VolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vols")
public class VolController {

    @Autowired
    private VolService volService;

    public VolController() {
        System.out.println("✅ VolController créé !");
    }

    // ✅ UNIQUEMENT CETTE méthode GET pour /api/vols
    @GetMapping
    public List<Vol> getAllVols() {
        System.out.println("🎯 GET /api/vols appelé !");
        List<Vol> vols = volService.getAllVols();
        System.out.println("🎯 " + vols.size() + " vols retournés");
        return vols;
    }

    // ✅ Méthode POST
    @PostMapping
    public Vol addVol(@RequestBody Vol vol) {
        System.out.println("🎯 POST /api/vols appelé");
        return volService.saveVol(vol);
    }

    // ✅ Méthode GET par ID
    @GetMapping("/{id}")
    public Vol getVolById(@PathVariable Long id) {
        return volService.getVolById(id);
    }

    // ✅ Méthode DELETE
    @DeleteMapping("/{id}")
    public void deleteVol(@PathVariable Long id) {
        volService.deleteVol(id);
    }

    // ✅ Méthode TEST simple (optionnelle)
    @GetMapping("/test")
    public String test() {
        return "✅ API Vols fonctionne ! " + System.currentTimeMillis();
    }
}