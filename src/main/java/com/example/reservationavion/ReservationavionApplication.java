package com.example.reservationavion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.example.reservationavion")
public class ReservationavionApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ReservationavionApplication.class, args);
        System.out.println("🎯 APPLICATION DÉMARRÉE - Scan des packages activé");
    }
}