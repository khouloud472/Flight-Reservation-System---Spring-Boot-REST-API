package com.example.reservationavion.web;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class SimpleWebController {

    // Page d'accueil
    @GetMapping("/")
    public String home() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Système de Réservation</title>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial; padding: 40px; background: #f5f5f5; }
                    .container { max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; }
                    h1 { color: #333; }
                    .btn { display: block; padding: 15px; margin: 10px 0; background: #4CAF50; color: white; text-decoration: none; border-radius: 5px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>✈️ Système de Réservation</h1>
                    <a href="/vols" class="btn">📋 Gestion des Vols</a>
                    <a href="/reservations" class="btn">🎫 Gestion des Réservations</a>
                    <a href="/swagger-ui.html" class="btn" style="background: #ff9800;">📚 API Docs</a>
                    <a href="/h2-console" class="btn" style="background: #9C27B0;">🗄️ Base H2</a>
                </div>
            </body>
            </html>
            """;
    }

    // Servir la page vols.html
    @GetMapping("/vols")
    public ResponseEntity<String> volsPage() {
        try {
            Resource resource = new ClassPathResource("static/vols.html");
            String htmlContent = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(htmlContent);
                    
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Servir la page reservations.html
    @GetMapping("/reservations")
    public ResponseEntity<String> reservationsPage() {
        try {
            Resource resource = new ClassPathResource("static/reservations.html");
            String htmlContent = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(htmlContent);
                    
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}