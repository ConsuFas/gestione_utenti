package com.exi.demo.exception;

public class DuplicateCodiceFiscaleException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateCodiceFiscaleException(String codiceFiscale ) {
        super("Esiste già un utente con il codice fiscale: " + codiceFiscale);
    }
}
