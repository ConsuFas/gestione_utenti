package com.exi.demo.service;

import java.util.List;

import com.exi.demo.dto.request.UtenteRequestDto;
import com.exi.demo.dto.response.UtenteResponseDto;



public interface UtenteService {

	UtenteResponseDto createUtente(UtenteRequestDto requestDto);

    List<UtenteResponseDto> getAllUtenti();

    UtenteResponseDto getUtenteById(Long id);

    UtenteResponseDto updateUtente(Long id, UtenteRequestDto requestDto);

    void deleteUtente(Long id);
    
    List<UtenteResponseDto> searchUtenti(String query);
}
