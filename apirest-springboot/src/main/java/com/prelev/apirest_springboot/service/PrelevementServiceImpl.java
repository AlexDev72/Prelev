package com.prelev.apirest_springboot.service;

import com.prelev.apirest_springboot.dto.PrelevementCreateDTO;
import com.prelev.apirest_springboot.dto.PrelevementResponseDTO;
import com.prelev.apirest_springboot.modele.Prelevement;
import com.prelev.apirest_springboot.modele.Utilisateur;
import com.prelev.apirest_springboot.repository.PrelevementRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrelevementServiceImpl implements PrelevementService {

    private final PrelevementRepository prelevementRepository;

    public PrelevementServiceImpl(PrelevementRepository prelevementRepository) {
        this.prelevementRepository = prelevementRepository;
    }

    @Override
    public PrelevementResponseDTO create(PrelevementCreateDTO dto, Utilisateur utilisateur) {
        if (dto.getDatePrelevement() == null) {
            throw new IllegalArgumentException("La date de prélèvement est obligatoire");
        }

        Prelevement prelevement = new Prelevement();
        prelevement.setNom(dto.getNom());
        prelevement.setPrix(dto.getPrix());
        java.sql.Date sqlDate = java.sql.Date.valueOf(dto.getDatePrelevement());
        prelevement.setDate_prelevement(sqlDate);
        prelevement.setUtilisateur(utilisateur);
        Prelevement saved = prelevementRepository.save(prelevement);
        return toResponseDTO(saved);
    }


    @Override
    public List<PrelevementResponseDTO> read(Utilisateur utilisateur) {
        return prelevementRepository.findByUtilisateur(utilisateur)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PrelevementResponseDTO update(Long id, PrelevementCreateDTO dto, Utilisateur utilisateur) {
        return prelevementRepository.findById(id)
                .filter(p -> p.getUtilisateur().getId().equals(utilisateur.getId()))
                .map(p -> {
                    p.setNom(dto.getNom());
                    p.setPrix(dto.getPrix());
                    p.setDate_prelevement(Date.valueOf(dto.getDatePrelevement()));
                    Prelevement updated = prelevementRepository.save(p);
                    return toResponseDTO(updated);
                })
                .orElseThrow(() -> new RuntimeException("Prélèvement non trouvé ou accès refusé"));
    }

    @Override
    public String delete(Long id, Utilisateur utilisateur) {
        Prelevement prelevement = prelevementRepository.findById(id)
                .filter(p -> p.getUtilisateur().getId().equals(utilisateur.getId()))
                .orElseThrow(() -> new RuntimeException("Prélèvement non trouvé ou accès refusé"));
        prelevementRepository.delete(prelevement);
        return "Prélèvement bien supprimé";
    }

    // Méthode utilitaire pour convertir une entité en DTO
    private PrelevementResponseDTO toResponseDTO(Prelevement prelevement) {
        PrelevementResponseDTO dto = new PrelevementResponseDTO();
        dto.setId(prelevement.getId());
        dto.setNom(prelevement.getNom());
        dto.setPrix(prelevement.getPrix());
        dto.setDatePrelevement(prelevement.getDate_prelevement().toLocalDate());
        return dto;
    }
}
