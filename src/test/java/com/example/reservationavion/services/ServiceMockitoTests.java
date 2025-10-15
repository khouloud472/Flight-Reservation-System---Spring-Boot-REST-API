package com.example.reservationavion.services;

import com.example.reservationavion.entities.Reservation;
import com.example.reservationavion.entities.Vol;
import com.example.reservationavion.repositories.ReservationRepository;
import com.example.reservationavion.repositories.VolRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ServiceMockitoTests {

	@Mock
    private ReservationRepository reservationRepository;

    @Mock
    private VolRepository volRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @InjectMocks
    private VolServiceImpl volService;

    private Vol testVol;
    private Reservation testReservation;

    @BeforeEach
    void setUp() {
        testVol = new Vol(1L, "Paris", "Lyon", "2024-01-15", 150);
        testReservation = new Reservation(1L, "Jean Dupont", 2, testVol);
    }

    // Tests pour VolService
    @Test
    void testGetAllVols() {
        // Given
        List<Vol> expectedVols = Arrays.asList(testVol);
        when(volRepository.findAll()).thenReturn(expectedVols);

        // When
        List<Vol> actualVols = volService.getAllVols();

        // Then
        assertNotNull(actualVols);
        assertEquals(1, actualVols.size());
        assertEquals("Paris", actualVols.get(0).getDestination());
        verify(volRepository, times(1)).findAll();
    }

    @Test
    void testSaveVol() {
        // Given
        when(volRepository.save(any(Vol.class))).thenReturn(testVol);

        // When
        Vol savedVol = volService.saveVol(testVol);

        // Then
        assertNotNull(savedVol);
        assertEquals(1L, savedVol.getId());
        assertEquals("Paris", savedVol.getDestination());
        verify(volRepository, times(1)).save(testVol);
    }

    @Test
    void testGetVolById_Found() {
        // Given
        when(volRepository.findById(1L)).thenReturn(Optional.of(testVol));

        // When
        Vol foundVol = volService.getVolById(1L);

        // Then
        assertNotNull(foundVol);
        assertEquals(1L, foundVol.getId());
        verify(volRepository, times(1)).findById(1L);
    }

    @Test
    void testGetVolById_NotFound() {
        // Given
        when(volRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Vol foundVol = volService.getVolById(999L);

        // Then
        assertNull(foundVol);
        verify(volRepository, times(1)).findById(999L);
    }

    @Test
    void testDeleteVol() {
        // When
        volService.deleteVol(1L);

        // Then
        verify(volRepository, times(1)).deleteById(1L);
    }

    // Tests pour ReservationService
    @Test
    void testGetAllReservations() {
        // Given
        List<Reservation> expectedReservations = Arrays.asList(testReservation);
        when(reservationRepository.findAll()).thenReturn(expectedReservations);

        // When
        List<Reservation> actualReservations = reservationService.getAllReservations();

        // Then
        assertNotNull(actualReservations);
        assertEquals(1, actualReservations.size());
        assertEquals("Jean Dupont", actualReservations.get(0).getNomClient());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testSaveReservation_Success() {
        // Given
        testVol.setPlacesDisponibles(10);
        when(volRepository.findById(1L)).thenReturn(Optional.of(testVol));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(testReservation);
        when(volRepository.save(any(Vol.class))).thenReturn(testVol);

        // When
        Reservation savedReservation = reservationService.saveReservation(testReservation);

        // Then
        assertNotNull(savedReservation);
        assertEquals(1L, savedReservation.getId());
        verify(volRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).save(testReservation);
        verify(volRepository, times(1)).save(testVol);
    }

    @Test
    void testSaveReservation_NotEnoughPlaces() {
        // Given
        testVol.setPlacesDisponibles(1);
        testReservation.setNombrePlaces(3);
        when(volRepository.findById(1L)).thenReturn(Optional.of(testVol));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> reservationService.saveReservation(testReservation));
        
        assertEquals("Pas assez de places disponibles sur ce vol", exception.getMessage());
        verify(volRepository, times(1)).findById(1L);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    void testSaveReservation_InvalidVol() {
        // Given
        testReservation.setVol(null);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> reservationService.saveReservation(testReservation));
        
        assertEquals("Vol invalide", exception.getMessage());
        verify(volRepository, never()).findById(any());
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    void testGetReservationById_Found() {
        // Given
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));

        // When
        Reservation foundReservation = reservationService.getReservationById(1L);

        // Then
        assertNotNull(foundReservation);
        assertEquals(1L, foundReservation.getId());
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetReservationById_NotFound() {
        // Given
        when(reservationRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Reservation foundReservation = reservationService.getReservationById(999L);

        // Then
        assertNull(foundReservation);
        verify(reservationRepository, times(1)).findById(999L);
    }

    @Test
    void testDeleteReservation() {
        // Given
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        when(volRepository.save(any(Vol.class))).thenReturn(testVol);

        // When
        reservationService.deleteReservation(1L);

        // Then
        verify(reservationRepository, times(1)).findById(1L);
        verify(volRepository, times(1)).save(testVol);
        verify(reservationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteReservation_NotFound() {
        // Given
        when(reservationRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        reservationService.deleteReservation(999L);

        // Then
        verify(reservationRepository, times(1)).findById(999L);
        verify(volRepository, never()).save(any(Vol.class));
        verify(reservationRepository, never()).deleteById(any());
    }

    @Test
    void testGetReservationsByVolId() {
        // Given
        List<Reservation> allReservations = Arrays.asList(testReservation);
        when(reservationRepository.findAll()).thenReturn(allReservations);

        // When
        List<Reservation> reservations = reservationService.getReservationsByVolId(1L);

        // Then
        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals(1L, reservations.get(0).getId());
        verify(reservationRepository, times(1)).findAll();
    }
}