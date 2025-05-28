package com.prelev.apirest_springboot.service;

import com.prelev.apirest_springboot.dto.ConnexionResponseDTO;
import com.prelev.apirest_springboot.modele.Utilisateur;
import com.prelev.apirest_springboot.repository.UtilisateurRepository;
import com.prelev.apirest_springboot.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ConnexionService {

    private final UtilisateurRepository utilisateurRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ConnexionService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public ConnexionResponseDTO login(String email, String mdp) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(mdp, utilisateur.getMdp())) {
            throw new RuntimeException("Mot de passe invalide");
        }

        // Génération du token JWT
        String token = JwtUtil.generateToken(email);

        return new ConnexionResponseDTO(token, utilisateur.getId());
    }
}
