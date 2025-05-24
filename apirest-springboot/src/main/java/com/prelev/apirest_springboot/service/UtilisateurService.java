package com.prelev.apirest_springboot.service;

import com.prelev.apirest_springboot.dto.UtilisateurCreateDTO;
import com.prelev.apirest_springboot.dto.UtilisateurResponseDTO;

import java.util.List;

public interface UtilisateurService {
    UtilisateurResponseDTO create(UtilisateurCreateDTO dto);
    List<UtilisateurResponseDTO> read();
    UtilisateurResponseDTO update(Long id, UtilisateurCreateDTO dto);
    String delete(Long id);
}
