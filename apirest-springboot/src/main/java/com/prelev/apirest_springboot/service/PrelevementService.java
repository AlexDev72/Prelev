package com.prelev.apirest_springboot.service;

import com.prelev.apirest_springboot.dto.*;
import com.prelev.apirest_springboot.modele.Utilisateur;

import java.math.BigDecimal;
import java.util.List;

public interface PrelevementService {
    PrelevementResponseDTO create(PrelevementCreateDTO dto, Utilisateur utilisateur);
    List<PrelevementResponseDTO> read(Utilisateur utilisateur);
    PrelevementResponseDTO update(Long id, PrelevementCreateDTO dto, Utilisateur utilisateur);
    String delete(Long id, Utilisateur utilisateur);
    List<PrelevementJourDTO> getPrelevementsAvecJour(Utilisateur utilisateur);
    List<PrelevementJourDTO> getPrelevementsPourJour(Utilisateur utilisateur, int jour);
    BigDecimal getTotalPrelevements(Utilisateur utilisateur);
    List<PrelevementResponseDTO> getPrelevementsAvenir(Utilisateur utilisateur);
    List<PrelevementParMoisDTO> getPrelevementsParMois(Long idUtilisateur);
    long countPrelevements(Long idUtilisateur);


}
