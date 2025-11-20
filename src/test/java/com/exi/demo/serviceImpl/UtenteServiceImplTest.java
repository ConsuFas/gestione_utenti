package com.exi.demo.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.exi.demo.dto.request.UtenteRequestDto;
import com.exi.demo.dto.response.UtenteResponseDto;
import com.exi.demo.exception.DuplicateCodiceFiscaleException;
import com.exi.demo.exception.UtenteNotFoundException;
import com.exi.demo.mapper.UtenteMapper;
import com.exi.demo.model.Utente;
import com.exi.demo.repository.UtenteRepository;

public class UtenteServiceImplTest {
	@Mock
    private UtenteRepository repo;

    @Mock
    private UtenteMapper map;

    private UtenteServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new UtenteServiceImpl(repo, map);
    }

    /* ====== TEST CREATE UTENTE ====== */

    @Test
    void createUtente_ok() {

        UtenteRequestDto dto = new UtenteRequestDto();
        dto.setNome("Mario");
        dto.setCognome("Rossi");
        dto.setCodiceFiscale("RSSMRA80A01H501U");
        dto.setDataNascita(LocalDate.of(1990, 1, 1));

        Utente entity = new Utente();
        entity.setId(1L);
        entity.setNome("Mario");
        entity.setCognome("Rossi");
        entity.setCodiceFiscale("RSSMRA80A01H501U");
        entity.setDataNascita(LocalDate.of(1990, 1, 1));

        UtenteResponseDto response = new UtenteResponseDto();
        response.setId(1L);
        response.setNome("Mario");
        response.setCognome("Rossi");
        response.setCodiceFiscale("RSSMRA80A01H501U");
        response.setDataNascita(LocalDate.of(1990, 1, 1));

        when(repo.findByCodiceFiscale(dto.getCodiceFiscale())).thenReturn(Optional.empty());
        when(map.toEntity(dto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(entity);
        when(map.toDto(entity)).thenReturn(response);

        UtenteResponseDto result = service.createUtente(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Mario", result.getNome());
        verify(repo).save(entity);
    }

    @Test
    void createUtente_cfDuplicato_lanciaDuplicateException() {

        UtenteRequestDto dto = new UtenteRequestDto();
        dto.setCodiceFiscale("RSSMRA80A01H501U");

        when(repo.findByCodiceFiscale(dto.getCodiceFiscale()))
                .thenReturn(Optional.of(new Utente()));

        assertThrows(DuplicateCodiceFiscaleException.class, () -> service.createUtente(dto));
    }

    /* ====== TEST GET UTENTE BY ID ====== */

    @Test
    void getUtenteById_nonEsistente_lanciaNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UtenteNotFoundException.class, () -> service.getUtenteById(99L));
    }

    /* ====== TEST DELETE UTENTE ====== */

    @Test
    void deleteUtente_nonEsistente_lanciaNotFound() {
        when(repo.existsById(10L)).thenReturn(false);

        assertThrows(UtenteNotFoundException.class, () -> service.deleteUtente(10L));
    }
}
