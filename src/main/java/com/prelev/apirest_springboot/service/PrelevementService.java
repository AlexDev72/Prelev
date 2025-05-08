package com.prelev.apirest_springboot.service;

import com.prelev.apirest_springboot.dto.PrelevementCreateDTO;
import com.prelev.apirest_springboot.dto.PrelevementResponseDTO;
import com.prelev.apirest_springboot.modele.Prelevement;

import java.util.List;

public interface PrelevementService {
    PrelevementResponseDTO create(PrelevementCreateDTO dto);
    List<PrelevementResponseDTO> read();  // Retourner une liste de PrelevementResponseDTO
    PrelevementResponseDTO update(Long id, PrelevementCreateDTO dto);
    String delete(Long id);
}
