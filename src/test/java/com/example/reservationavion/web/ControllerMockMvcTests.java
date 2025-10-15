package com.example.reservationavion.web;

import com.example.reservationavion.entities.Reservation;
import com.example.reservationavion.entities.Vol;
import com.example.reservationavion.services.ReservationService;
import com.example.reservationavion.services.VolService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({VolController.class, ReservationController.class})
class ControllerMockMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolService volService;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    private Vol testVol;
    private Reservation testReservation;

    @BeforeEach
    void setUp() {
        testVol = new Vol(1L, "Paris", "Lyon", "2024-01-15", 150);
        testReservation = new Reservation(1L, "Jean Dupont", 2, testVol);
    }

    // Tests pour VolController avec URLs
    @Test
    void testGetAllVols_Endpoint() throws Exception {
        // Given
        List<Vol> vols = Arrays.asList(testVol);
        when(volService.getAllVols()).thenReturn(vols);

        // When & Then
        mockMvc.perform(get("/api/vols"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].destination", is("Paris")))
                .andExpect(jsonPath("$[0].depart", is("Lyon")));

        verify(volService, times(1)).getAllVols();
    }

    @Test
    void testAddVol_Endpoint() throws Exception {
        // Given
        when(volService.saveVol(any(Vol.class))).thenReturn(testVol);

        // When & Then
        mockMvc.perform(post("/api/vols")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testVol)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.destination", is("Paris")));

        verify(volService, times(1)).saveVol(any(Vol.class));
    }

    @Test
    void testGetVolById_Endpoint() throws Exception {
        // Given
        when(volService.getVolById(1L)).thenReturn(testVol);

        // When & Then
        mockMvc.perform(get("/api/vols/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.destination", is("Paris")));

        verify(volService, times(1)).getVolById(1L);
    }

    @Test
    void testDeleteVol_Endpoint() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/vols/1"))
                .andExpect(status().isOk());

        verify(volService, times(1)).deleteVol(1L);
    }

    @Test
    void testVolTest_Endpoint() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/vols/test"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("✅ API Vols fonctionne !")));
    }

    // Tests pour ReservationController avec URLs
    @Test
    void testGetAllReservations_Endpoint() throws Exception {
        // Given
        List<Reservation> reservations = Arrays.asList(testReservation);
        when(reservationService.getAllReservations()).thenReturn(reservations);

        // When & Then
        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nomClient", is("Jean Dupont")))
                .andExpect(jsonPath("$[0].nombrePlaces", is(2)));

        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    void testCreateReservation_Success_Endpoint() throws Exception {
        // Given
        when(reservationService.saveReservation(any(Reservation.class))).thenReturn(testReservation);

        // When & Then
        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testReservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nomClient", is("Jean Dupont")));

        verify(reservationService, times(1)).saveReservation(any(Reservation.class));
    }

    @Test
    void testCreateReservation_Error_Endpoint() throws Exception {
        // Given
        when(reservationService.saveReservation(any(Reservation.class)))
                .thenThrow(new RuntimeException("Pas assez de places"));

        // When & Then
        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testReservation)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Pas assez de places"));

        verify(reservationService, times(1)).saveReservation(any(Reservation.class));
    }

    @Test
    void testGetReservationById_Found_Endpoint() throws Exception {
        // Given
        when(reservationService.getReservationById(1L)).thenReturn(testReservation);

        // When & Then
        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nomClient", is("Jean Dupont")));

        verify(reservationService, times(1)).getReservationById(1L);
    }

    @Test
    void testGetReservationById_NotFound_Endpoint() throws Exception {
        // Given
        when(reservationService.getReservationById(999L)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/reservations/999"))
                .andExpect(status().isNotFound());

        verify(reservationService, times(1)).getReservationById(999L);
    }

    @Test
    void testDeleteReservation_Endpoint() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isOk());

        verify(reservationService, times(1)).deleteReservation(1L);
    }

    @Test
    void testGetReservationsByVolId_Endpoint() throws Exception {
        // Given
        List<Reservation> reservations = Arrays.asList(testReservation);
        when(reservationService.getReservationsByVolId(1L)).thenReturn(reservations);

        // When & Then
        mockMvc.perform(get("/api/reservations/vol/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

        verify(reservationService, times(1)).getReservationsByVolId(1L);
    }

    @Test
    void testReservationTest_Endpoint() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/reservations/test"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("✅ API Réservations fonctionne !")));
    }
}