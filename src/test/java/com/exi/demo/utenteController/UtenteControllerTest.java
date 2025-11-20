package com.exi.demo.utenteController;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.exi.demo.controller.UtenteController;
import com.exi.demo.dto.response.UtenteResponseDto;
import com.exi.demo.service.UtenteService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UtenteController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UtenteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    
    private UtenteService service;

    @Test
    void getUtenteById_ok() throws Exception {

        UtenteResponseDto dto =
                new UtenteResponseDto(1L, "Mario", "Rossi", "RSSMRA80A01H501U",
                        LocalDate.of(1990, 1, 1));

        when(service.getUtenteById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/utenti/getUtenteById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Mario"))
                .andExpect(jsonPath("$.cognome").value("Rossi"));
    }

    @Test
    void getAllUtenti_ok() throws Exception {

        List<UtenteResponseDto> lista = Arrays.asList(
                new UtenteResponseDto(1L, "Mario", "Rossi", "CF1", LocalDate.of(1990,1,1)),
                new UtenteResponseDto(2L, "Luca", "Bianchi", "CF2", LocalDate.of(1992,2,2))
        );

        when(service.getAllUtenti()).thenReturn(lista);

        mockMvc.perform(get("/api/utenti/getAllUtenti"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Mario"))
                .andExpect(jsonPath("$[1].nome").value("Luca"));
    }
}
