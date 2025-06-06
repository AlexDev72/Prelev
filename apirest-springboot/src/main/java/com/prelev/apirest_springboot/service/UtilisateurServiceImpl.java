package com.prelev.apirest_springboot.service;

import com.prelev.apirest_springboot.dto.UtilisateurCreateDTO;
import com.prelev.apirest_springboot.dto.UtilisateurResponseDTO;
import com.prelev.apirest_springboot.modele.Utilisateur;
import com.prelev.apirest_springboot.repository.UtilisateurRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UtilisateurResponseDTO create(UtilisateurCreateDTO dto) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setAge(dto.getAge());

        // Hash du mot de passe avant sauvegarde
        utilisateur.setMdp(passwordEncoder.encode(dto.getMdp()));

        Utilisateur saved = utilisateurRepository.save(utilisateur);
        return toResponseDTO(saved);
    }

    @Override
    public UtilisateurResponseDTO findById(Long id) {
        return utilisateurRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));
    }

    @Override
    public Optional<UtilisateurResponseDTO> findByUsername(String username) {
        return Optional.empty();
    }


    @Override
    public Optional<UtilisateurResponseDTO> findOptionalById(Long id) {
        return utilisateurRepository.findById(id)
                .map(this::toResponseDTO);
    }

    @Override
    public List<UtilisateurResponseDTO> read() {
        return utilisateurRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UtilisateurResponseDTO update(Long id, UtilisateurCreateDTO dto) {
        return utilisateurRepository.findById(id)
                .map(u -> {
                    u.setNom(dto.getNom());
                    u.setPrenom(dto.getPrenom());
                    u.setAge(dto.getAge());

                    // Si mot de passe modifié, hash le nouveau
                    if (dto.getMdp() != null && !dto.getMdp().isBlank()) {
                        u.setMdp(passwordEncoder.encode(dto.getMdp()));
                    }

                    Utilisateur updated = utilisateurRepository.save(u);
                    return toResponseDTO(updated);
                })
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @Override
    public String delete(Long id) {
        utilisateurRepository.deleteById(id);
        return "Utilisateur supprimé";
    }

    private UtilisateurResponseDTO toResponseDTO(Utilisateur utilisateur) {
        UtilisateurResponseDTO dto = new UtilisateurResponseDTO();
        dto.setId(utilisateur.getId());
        dto.setEmail(utilisateur.getEmail());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());
        dto.setAge(utilisateur.getAge());
        return dto;
    }
}