package com.prelev.apirest_springboot.service;

import com.prelev.apirest_springboot.dto.*;
import com.prelev.apirest_springboot.modele.Prelevement;
import com.prelev.apirest_springboot.modele.Utilisateur;
import com.prelev.apirest_springboot.repository.PrelevementRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrelevementServiceImpl implements PrelevementService {

    private final PrelevementRepository prelevementRepository;


    public PrelevementServiceImpl(PrelevementRepository prelevementRepository) {
        this.prelevementRepository = prelevementRepository;
    }

    @Override
    @Transactional
    public PrelevementResponseDTO create(PrelevementCreateDTO dto, Utilisateur utilisateur) {
        // Validation des données
        if (dto.getDatePrelevement() == null) {
            throw new IllegalArgumentException("La date de prélèvement est obligatoire");
        }
        if (utilisateur == null) {
            throw new IllegalArgumentException("Utilisateur doit être authentifié");
        }

        // Création du prélèvement
        Prelevement prelevement = new Prelevement();
        prelevement.setNom(dto.getNom());
        prelevement.setPrix(dto.getPrix());
        prelevement.setDate_prelevement(dto.getDatePrelevement());
        prelevement.setUtilisateur(utilisateur);

        return toResponseDTO(prelevementRepository.save(prelevement));
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
                    p.setDate_prelevement(dto.getDatePrelevement());
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
        dto.setDatePrelevement(prelevement.getDate_prelevement());
        return dto;
    }

    public List<PrelevementJourDTO> getPrelevementsAvecJour(Utilisateur utilisateur) {
        return prelevementRepository.findByUtilisateur(utilisateur)
                .stream()
                .map(p -> new PrelevementJourDTO(
                        p.getNom(),
                        p.getPrix(),
                        p.getDate_prelevement().getDayOfMonth()
                ))
                .collect(Collectors.toList());
    }

    public List<PrelevementJourDTO> getPrelevementsPourJour(Utilisateur utilisateur, int jour) {
        List<Prelevement> prelevements = prelevementRepository.findByUtilisateur(utilisateur);

        return prelevements.stream()
                .filter(p -> {
                    LocalDate date = p.getDate_prelevement();
                    return date.getDayOfMonth() == jour;
                })
                .map(p -> {
                    LocalDate date = p.getDate_prelevement();
                    return new PrelevementJourDTO(
                            p.getNom(),
                            p.getPrix(),
                            date.getDayOfMonth()
                    );
                })
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalPrelevements(Utilisateur utilisateur) {
        return prelevementRepository.findByUtilisateur(utilisateur)
                .stream()
                .map(Prelevement::getPrix)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // prélèvements avenir du mois
    public List<PrelevementResponseDTO> getPrelevementsAvenir(Utilisateur utilisateur) {
        LocalDate aujourdHui = LocalDate.now();

        List<Prelevement> prelevementsBruts = prelevementRepository.findByUtilisateur(utilisateur);

        List<PrelevementResponseDTO> prelevementsAvenir = prelevementsBruts.stream()
                .filter(p -> {
                    int jourPrelev = p.getDate_prelevement().getDayOfMonth();
                    int jourAuj = aujourdHui.getDayOfMonth();
                    boolean isAfter = jourPrelev > jourAuj;

                    return isAfter;
                })
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        return prelevementsAvenir;
    }


    // prélèvements par mois (liste des prélèvements sans date de fin)
    public List<PrelevementParMoisDTO> getPrelevementsParMois(Long idUtilisateur) {
        LocalDate today = LocalDate.now();

        return prelevementRepository.findByUtilisateur_Id(idUtilisateur).stream()
                .filter(p -> p.getDate_fin() == null || p.getDate_fin().isAfter(today))
                .map(p -> new PrelevementParMoisDTO(
                        p.getId(),
                        p.getNom(),
                        String.format("%02d", p.getDate_prelevement().getDayOfMonth()), // on ne garde que le jour
                        p.getPrix()
                ))
                .collect(Collectors.toList());
    }

    public long countPrelevements(Long idUtilisateur) {
        LocalDate today = LocalDate.now();

        return prelevementRepository.findByUtilisateur_Id(idUtilisateur).stream()
                .filter(p -> p.getDate_fin() == null || p.getDate_fin().isAfter(today))
                .count();
    }


}
