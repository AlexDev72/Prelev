package com.prelev.apirest_springboot.service;

import com.prelev.apirest_springboot.dto.UtilisateurCreateDTO;
import com.prelev.apirest_springboot.dto.UtilisateurResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
    UtilisateurResponseDTO create(UtilisateurCreateDTO dto);

    Optional<UtilisateurResponseDTO> findOptionalById(Long id);

    List<UtilisateurResponseDTO> read();
    UtilisateurResponseDTO update(Long id, UtilisateurCreateDTO dto);
    String delete(Long id);
    UtilisateurResponseDTO findById(Long id); // Nouvelle méthode
    // Méthode pour trouver un utilisateur par nom d'utilisateur (si vous avez ce champ)
    Optional<UtilisateurResponseDTO> findByUsername(String username);
}
