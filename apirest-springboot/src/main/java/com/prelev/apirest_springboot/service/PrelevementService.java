package com.prelev.apirest_springboot.service;

import com.prelev.apirest_springboot.dto.PrelevementCreateDTO;
import com.prelev.apirest_springboot.dto.PrelevementResponseDTO;
import com.prelev.apirest_springboot.modele.Prelevement;
import com.prelev.apirest_springboot.modele.Utilisateur;

import java.util.List;

public interface PrelevementService {
    PrelevementResponseDTO create(PrelevementCreateDTO dto, Utilisateur utilisateur);
    List<PrelevementResponseDTO> read(Utilisateur utilisateur);
    PrelevementResponseDTO update(Long id, PrelevementCreateDTO dto, Utilisateur utilisateur);
    String delete(Long id, Utilisateur utilisateur);
}
