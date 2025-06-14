package com.prelev.apirest_springboot.service;

import com.prelev.apirest_springboot.dto.PrelevementCountParMoisDTO;
import com.prelev.apirest_springboot.dto.PrelevementCreateDTO;
import com.prelev.apirest_springboot.dto.PrelevementJourDTO;
import com.prelev.apirest_springboot.dto.PrelevementResponseDTO;
import com.prelev.apirest_springboot.modele.Prelevement;
import com.prelev.apirest_springboot.modele.Utilisateur;

import java.util.List;

public interface PrelevementService {
    PrelevementResponseDTO create(PrelevementCreateDTO dto, Utilisateur utilisateur);
    List<PrelevementResponseDTO> read(Utilisateur utilisateur);
    PrelevementResponseDTO update(Long id, PrelevementCreateDTO dto, Utilisateur utilisateur);
    String delete(Long id, Utilisateur utilisateur);
    List<PrelevementJourDTO> getPrelevementsAvecJour(Utilisateur utilisateur);
    List<PrelevementJourDTO> getPrelevementsPourJour(Utilisateur utilisateur, int jour);
    double getTotalPrelevements(Utilisateur utilisateur);
    List<PrelevementResponseDTO> getPrelevementsAvenir(Utilisateur utilisateur);
    List<PrelevementCountParMoisDTO> getNombrePrelevementsParMois(Utilisateur utilisateur);

}
