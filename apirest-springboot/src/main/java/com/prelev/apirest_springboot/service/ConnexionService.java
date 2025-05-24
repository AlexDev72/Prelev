package com.prelev.apirest_springboot.service;

import com.prelev.apirest_springboot.dto.ConnexionResponseDTO;
import com.prelev.apirest_springboot.modele.Utilisateur;
import com.prelev.apirest_springboot.repository.UtilisateurRepository;
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

        // Ici tu peux générer un token JWT, ou retourner un message simple
        String fakeToken = "token-simule-pour-demo";

        return new ConnexionResponseDTO(fakeToken);
    }
}
