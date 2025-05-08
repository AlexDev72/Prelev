package com.prelev.apirest_springboot.service;

import com.prelev.apirest_springboot.dto.PrelevementCreateDTO;
import com.prelev.apirest_springboot.dto.PrelevementResponseDTO;
import com.prelev.apirest_springboot.modele.Prelevement;
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
    public PrelevementResponseDTO create(PrelevementCreateDTO dto) {
        // Conversion DTO -> Entité
        Prelevement prelevement = new Prelevement();
        prelevement.setNom(dto.getNom());
        prelevement.setPrix(dto.getPrix());
        prelevement.setDate_prelevement(Date.valueOf(dto.getDatePrelevement()));
        // Log de la date avant la sauvegarde
        System.out.println("Date Prelevement: " + prelevement.getDate_prelevement());
        Prelevement saved = prelevementRepository.save(prelevement);

        // Conversion Entité -> DTO
        return toResponseDTO(saved);
    }

    @Override
    public List<PrelevementResponseDTO> read() {
        // Conversion de chaque entité vers DTO
        return prelevementRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PrelevementResponseDTO update(Long id, PrelevementCreateDTO dto) {
        // Conversion DTO -> Entité
        Prelevement prelevement = new Prelevement();
        prelevement.setNom(dto.getNom());
        prelevement.setPrix(dto.getPrix());
        prelevement.setDate_prelevement(Date.valueOf(dto.getDatePrelevement()));

        return prelevementRepository.findById(id)
                .map(p -> {
                    // Mise à jour des données dans l'entité
                    p.setNom(prelevement.getNom());
                    p.setPrix(prelevement.getPrix());
                    p.setDate_prelevement(prelevement.getDate_prelevement());
                    Prelevement updated = prelevementRepository.save(p);

                    // Conversion Entité -> DTO
                    return toResponseDTO(updated);
                })
                .orElseThrow(() -> new RuntimeException("Prelevement non trouvé " + id));
    }

    @Override
    public String delete(Long id) {
        prelevementRepository.deleteById(id);
        return "Prélèvement bien supprimé";
    }

    // Méthode utilitaire pour convertir une entité en DTO
    private PrelevementResponseDTO toResponseDTO(Prelevement prelevement) {
        PrelevementResponseDTO dto = new PrelevementResponseDTO();
        dto.setId(prelevement.getId());
        dto.setNom(prelevement.getNom());
        dto.setPrix(prelevement.getPrix());
        dto.setDatePrelevement(prelevement.getDate_prelevement().toLocalDate()); // conversion vers LocalDate
        return dto;
    }
}
