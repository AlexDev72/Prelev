package com.prelev.apirest_springboot.controller;

import com.prelev.apirest_springboot.dto.PrelevementCreateDTO;
import com.prelev.apirest_springboot.dto.PrelevementJourDTO;
import com.prelev.apirest_springboot.dto.PrelevementParMoisDTO;
import com.prelev.apirest_springboot.dto.PrelevementResponseDTO;
import com.prelev.apirest_springboot.modele.Utilisateur;
import com.prelev.apirest_springboot.repository.UtilisateurRepository;
import com.prelev.apirest_springboot.security.CustomUserDetails;
import com.prelev.apirest_springboot.service.PrelevementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://192.168.1.22:3000", allowCredentials = "true")
@RestController
@RequestMapping("/prelevement")
public class PrelevementController {

    private final PrelevementService prelevementService;
    private final UtilisateurRepository utilisateurRepository;


    public PrelevementController(PrelevementService prelevementService, UtilisateurRepository utilisateurRepository) {
        this.prelevementService = prelevementService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping("/cree")
    public ResponseEntity<?> createPrelevement(
            @Valid @RequestBody PrelevementCreateDTO dto,
            Authentication authentication) {

        // Méthode robuste pour récupérer l'utilisateur
        Utilisateur utilisateur = null;
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            utilisateur = ((CustomUserDetails) authentication.getPrincipal()).getUtilisateur();
        }

        if (utilisateur == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\": \"Authentification invalide\"}");
        }

        return ResponseEntity.ok(prelevementService.create(dto, utilisateur));
    }

    @GetMapping("/lire")
    public List<PrelevementResponseDTO> read() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        Utilisateur utilisateur = userDetails.getUtilisateur();

        return prelevementService.read(utilisateur);
    }

    @PutMapping("/modifier/{id}")
    public PrelevementResponseDTO update(@PathVariable Long id,
                                         @RequestBody PrelevementCreateDTO dto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        Utilisateur utilisateur = userDetails.getUtilisateur();

        return prelevementService.update(id, dto, utilisateur);
    }

    @DeleteMapping("/supprimer/{id}")
    public String delete(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        Utilisateur utilisateur = userDetails.getUtilisateur();

        return prelevementService.delete(id, utilisateur);
    }

    @GetMapping("/liredate")
    public List<PrelevementJourDTO> lireDate() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        Utilisateur utilisateur = userDetails.getUtilisateur();

        return prelevementService.getPrelevementsAvecJour(utilisateur);
    }

    @GetMapping("/liredetail")
    public List<PrelevementJourDTO> lireDetail(@RequestParam("jour") int jour) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        Utilisateur utilisateur = userDetails.getUtilisateur();

        return prelevementService.getPrelevementsPourJour(utilisateur, jour);
    }

    @GetMapping("/total")
    public double getTotalPrelevements() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        Utilisateur utilisateur = userDetails.getUtilisateur();

        return prelevementService.getTotalPrelevements(utilisateur);
    }

    @GetMapping("/totalprelev")
    public double getCountPrelevements() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        Utilisateur utilisateur = userDetails.getUtilisateur();

        return prelevementService.countPrelevements(utilisateur.getId());
    }


    @GetMapping("/avenir")
    public List<PrelevementResponseDTO> getPrelevementsAvenir() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        Utilisateur utilisateur = userDetails.getUtilisateur();

        return prelevementService.getPrelevementsAvenir(utilisateur);
    }

    @GetMapping("/par-mois")
    public List<PrelevementParMoisDTO> getPrelevementsParMois() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        Utilisateur utilisateur = userDetails.getUtilisateur();

        return prelevementService.getPrelevementsParMois(utilisateur.getId());
    }

}
