package com.exi.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exi.demo.dto.request.UtenteRequestDto;
import com.exi.demo.dto.response.UtenteResponseDto;
import com.exi.demo.service.UtenteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/utenti")
@CrossOrigin("*")
public class UtenteController {
	
	@Autowired
	private UtenteService service;

	
	public UtenteController(UtenteService service) {
		super();
		this.service = service;
	}
	
	// --- CREATE ---
   
    @PostMapping("/createUtente")
    public ResponseEntity<UtenteResponseDto> create( @Valid @RequestBody UtenteRequestDto dto) {

        UtenteResponseDto creato = service.createUtente(dto);
        return ResponseEntity.ok(creato);
    }

    // --- GET ALL ---
   
    @GetMapping("/getAllUtenti")
    public ResponseEntity<List<UtenteResponseDto>> getAll() {

        List<UtenteResponseDto> utenti = service.getAllUtenti();
        return ResponseEntity.ok(utenti);
    }

    // --- GET BY ID ---
  
    @GetMapping("getUtenteById/{id}")
    public ResponseEntity<UtenteResponseDto> getById(@PathVariable Long id) {

        UtenteResponseDto utente = service.getUtenteById(id);
        return ResponseEntity.ok(utente);
    }

    // --- UPDATE ---

    @PutMapping("updateUtente/{id}")
    public ResponseEntity<UtenteResponseDto> update(@PathVariable Long id,
                                                    @RequestBody @Valid UtenteRequestDto dto) {

        UtenteResponseDto aggiornato = service.updateUtente(id, dto);
        return ResponseEntity.ok(aggiornato);
    }

    // --- DELETE ---

    @DeleteMapping("deleteUtente/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.deleteUtente(id);
        return ResponseEntity.noContent().build();
    }

    // --- SEARCH ---
    // Eseguo la ricerca utenti usando la query 'q'
    // La ricerca interna al service gestisce codice fiscale, nome e cognome
    @GetMapping("/ricercaUtente")
    public ResponseEntity<List<UtenteResponseDto>> search(@RequestParam("q") String query) {

        List<UtenteResponseDto> risultati = service.searchUtenti(query);
        return ResponseEntity.ok(risultati);
    }
	
	
}
