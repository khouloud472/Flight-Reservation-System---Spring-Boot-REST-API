package com.example.reservationavion.web;

import com.example.reservationavion.entities.Reservation;
import com.example.reservationavion.entities.Vol;
import com.example.reservationavion.services.ReservationService;
import com.example.reservationavion.services.VolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerMockitoTests {

    @Mock
    private VolService volService;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private VolController volController;

    @InjectMocks
    private ReservationController reservationController;

    private Vol testVol;
    private Reservation testReservation;

    @BeforeEach
    void setUp() {
        testVol = new Vol(1L, "Paris", "Lyon", "2024-01-15", 150);
        testReservation = new Reservation(1L, "Jean Dupont", 2, testVol);
    }

    // Tests pour VolController
    @Test
    void testGetAllVols() {
        // Given
        List<Vol> expectedVols = Arrays.asList(testVol);
        when(volService.getAllVols()).thenReturn(expectedVols);

        // When
        List<Vol> actualVols = volController.getAllVols();

        // Then
        assertNotNull(actualVols);
        assertEquals(1, actualVols.size());
        assertEquals("Paris", actualVols.get(0).getDestination());
        verify(volService, times(1)).getAllVols();
    }

    @Test
    void testAddVol() {
        // Given
        when(volService.saveVol(any(Vol.class))).thenReturn(testVol);

        // When
        Vol result = volController.addVol(testVol);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(volService, times(1)).saveVol(testVol);
    }

    @Test
    void testGetVolById() {
        // Given
        when(volService.getVolById(1L)).thenReturn(testVol);

        // When
        Vol result = volController.getVolById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(volService, times(1)).getVolById(1L);
    }

    @Test
    void testDeleteVol() {
        // When
        volController.deleteVol(1L);

        // Then
        verify(volService, times(1)).deleteVol(1L);
    }

    @Test
    void testVolTestEndpoint() {
        // When
        String result = volController.test();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("✅ API Vols fonctionne !"));
    }

    // Tests pour ReservationController
    @Test
    void testGetAllReservations() {
        // Given
        List<Reservation> expectedReservations = Arrays.asList(testReservation);
        when(reservationService.getAllReservations()).thenReturn(expectedReservations);

        // When
        List<Reservation> actualReservations = reservationController.getAllReservations();

        // Then
        assertNotNull(actualReservations);
        assertEquals(1, actualReservations.size());
        assertEquals("Jean Dupont", actualReservations.get(0).getNomClient());
        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    void testCreateReservation_Success() {
        // Given
        when(reservationService.saveReservation(any(Reservation.class))).thenReturn(testReservation);

        // When
        ResponseEntity<?> response = reservationController.createReservation(testReservation);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Reservation);
        verify(reservationService, times(1)).saveReservation(testReservation);
    }

    @Test
    void testCreateReservation_Error() {
        // Given
        when(reservationService.saveReservation(any(Reservation.class)))
            .thenThrow(new RuntimeException("Pas assez de places"));

        // When
        ResponseEntity<?> response = reservationController.createReservation(testReservation);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Pas assez de places", response.getBody());
        verify(reservationService, times(1)).saveReservation(testReservation);
    }

    @Test
    void testGetReservationById_Found() {
        // Given
        when(reservationService.getReservationById(1L)).thenReturn(testReservation);

        // When
        ResponseEntity<Reservation> response = reservationController.getReservationById(1L);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(reservationService, times(1)).getReservationById(1L);
    }

    @Test
    void testGetReservationById_NotFound() {
        // Given
        when(reservationService.getReservationById(999L)).thenReturn(null);

        // When
        ResponseEntity<Reservation> response = reservationController.getReservationById(999L);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(reservationService, times(1)).getReservationById(999L);
    }

    @Test
    void testDeleteReservation() {
        // When
        ResponseEntity<Void> response = reservationController.deleteReservation(1L);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reservationService, times(1)).deleteReservation(1L);
    }

    @Test
    void testGetReservationsByVolId() {
        // Given
        List<Reservation> expectedReservations = Arrays.asList(testReservation);
        when(reservationService.getReservationsByVolId(1L)).thenReturn(expectedReservations);

        // When
        List<Reservation> result = reservationController.getReservationsByVolId(1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reservationService, times(1)).getReservationsByVolId(1L);
    }

    @Test
    void testReservationTestEndpoint() {
        // When
        String result = reservationController.test();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("✅ API Réservations fonctionne !"));
    }
}