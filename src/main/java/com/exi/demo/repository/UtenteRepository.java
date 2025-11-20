package com.exi.demo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exi.demo.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long>{

	Optional<Utente> findByCodiceFiscale(String codiceFiscale);

    List<Utente> findByNomeContainingIgnoreCase(String nome);

    List<Utente> findByCognomeContainingIgnoreCase(String cognome);
}
