package com.exi.demo.mapper;

import org.springframework.stereotype.Component;

import com.exi.demo.dto.request.UtenteRequestDto;
import com.exi.demo.dto.response.UtenteResponseDto;
import com.exi.demo.model.Utente;

@Component
public class UtenteMapper {
	
	public Utente toEntity(UtenteRequestDto dto) {
		
		Utente u = new Utente();
		u.setNome(dto.getNome());
		u.setCognome(dto.getCognome());
		u.setCodiceFiscale(dto.getCodiceFiscale());
		u.setDataNascita(dto.getDataNascita());
		
		return u;
	}
	
	public UtenteResponseDto toDto(Utente u) {
		
		UtenteResponseDto dto = new UtenteResponseDto();
		dto.setId(u.getId());
		dto.setNome(u.getNome());
		dto.setCognome(u.getCognome());
		dto.setCodiceFiscale(u.getCodiceFiscale());
		dto.setDataNascita(u.getDataNascita());
		
		return dto;
	}

}
