package com.exi.demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exi.demo.dto.request.UtenteRequestDto;
import com.exi.demo.dto.response.UtenteResponseDto;
import com.exi.demo.exception.DuplicateCodiceFiscaleException;
import com.exi.demo.exception.UtenteNotFoundException;
import com.exi.demo.mapper.UtenteMapper;
import com.exi.demo.model.Utente;
import com.exi.demo.repository.UtenteRepository;
import com.exi.demo.service.UtenteService;

@Service
public class UtenteServiceImpl implements UtenteService {

    @Autowired
    private UtenteRepository repo;
    private UtenteMapper map;

    //-- COSTRUTTORE --

    public UtenteServiceImpl(UtenteRepository repo, UtenteMapper map) {
        super();
        this.repo = repo;
        this.map = map;
    }

    //-- INIZIO IMPL METODI CRUD --

    @Override
    public UtenteResponseDto createUtente(UtenteRequestDto dto) {

        Optional<Utente> esistente = repo.findByCodiceFiscale(dto.getCodiceFiscale());
        if (esistente.isPresent()) {
            // uso eccezione custom per CF duplicato
            throw new DuplicateCodiceFiscaleException(dto.getCodiceFiscale());
        }

        Utente u = map.toEntity(dto);
        Utente salvato = repo.save(u);
        return map.toDto(salvato);
    }

    @Override
    public List<UtenteResponseDto> getAllUtenti() {

        List<Utente> utenti = repo.findAll();
        List<UtenteResponseDto> d = new ArrayList<>();

        for (Utente u : utenti) {
            d.add(map.toDto(u));
        }

        return d;
    }

    @Override
    public UtenteResponseDto getUtenteById(Long id) {

        Utente u = getUtenteOrThrow(id);
        return map.toDto(u);
    }

    @Override
    public UtenteResponseDto updateUtente(Long id, UtenteRequestDto dto) {

        // controllo che il CF non sia usato da un altro utente
        Optional<Utente> altro = repo.findByCodiceFiscale(dto.getCodiceFiscale());
        if (altro.isPresent() && !altro.get().getId().equals(id)) {
            throw new DuplicateCodiceFiscaleException(dto.getCodiceFiscale());
        }

        Utente esistente = getUtenteOrThrow(id);

        esistente.setNome(dto.getNome());
        esistente.setCognome(dto.getCognome());
        esistente.setCodiceFiscale(dto.getCodiceFiscale());
        esistente.setDataNascita(dto.getDataNascita());

        Utente aggiornato = repo.save(esistente);
        return map.toDto(aggiornato);
    }

    @Override
    public void deleteUtente(Long id) {

        if (!repo.existsById(id)) {
            // se l'ID non esiste sollevo eccezione 404
            throw new UtenteNotFoundException(id);
        }

        repo.deleteById(id);
    }

    @Override
    public List<UtenteResponseDto> searchUtenti(String query) {

        if (query == null || query.isBlank()) {
            return getAllUtenti();
        }

        String q = query.trim();
        List<Utente> risultati = new ArrayList<>();

        // --- 1) Ricerca per codice fiscale (match esatto) ---
        Optional<Utente> cf = repo.findByCodiceFiscale(q);
        if (cf.isPresent()) {
            Utente u = cf.get();

            // Aggiungo l'utente alla lista solo se non è già presente
            if (!contieneUtenteConId(risultati, u.getId())) {
                risultati.add(u);
            }
        }

        // --- 2) Ricerca full-text sul nome ---
        List<Utente> byNome = repo.findByNomeContainingIgnoreCase(q);
        if (byNome != null) {
            for (Utente u : byNome) {
                if (!contieneUtenteConId(risultati, u.getId())) {
                    risultati.add(u);
                }
            }
        }

        // --- 3) Ricerca full-text sul cognome ---
        List<Utente> byCognome = repo.findByCognomeContainingIgnoreCase(q);
        if (byCognome != null) {
            for (Utente u : byCognome) {
                if (!contieneUtenteConId(risultati, u.getId())) {
                    risultati.add(u);
                }
            }
        }

        // --- 4) Converto la lista di Utente in lista di UtenteResponseDto ---
        List<UtenteResponseDto> dtoList = new ArrayList<>();

        for (Utente u : risultati) {
            dtoList.add(map.toDto(u));
        }

        return dtoList;
    }

    //-- INIZIO METODI DI SUPPORTO --

    private boolean contieneUtenteConId(List<Utente> lista, Long id) {
        if (id == null) {
            return false;
        }
        for (Utente u : lista) {
            if (id.equals(u.getId())) {
                return true;
            }
        }
        return false;
    }

    private Utente getUtenteOrThrow(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new UtenteNotFoundException(id));
    }
}
