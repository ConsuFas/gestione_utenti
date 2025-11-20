package com.exi.demo.exception;

public class UtenteNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UtenteNotFoundException(Long id) {
        super("Utente non trovato con ID: " + id);
    }
}
