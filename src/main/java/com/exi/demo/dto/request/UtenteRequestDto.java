package com.exi.demo.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UtenteRequestDto {
	
	@NotBlank
	private String nome;
	@NotBlank
    private String cognome;
    
    @Size(min = 16, max = 16)
    @NotBlank
    private String codiceFiscale;
    
    @NotNull
    private LocalDate dataNascita;
    
    //--COSTRUTTORI--
	public UtenteRequestDto() {
		super();
	}


	public UtenteRequestDto(String nome, String cognome, String codiceFiscale, LocalDate dataNascita) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataNascita = dataNascita;
	}

	//--GETTERS AND SETTERS--
	
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public LocalDate getDataNascita() {
		return dataNascita;
	}


	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}
    
    
}
