package com.prelev.apirest_springboot.controller;

import com.prelev.apirest_springboot.dto.PrelevementCountParMoisDTO;
import com.prelev.apirest_springboot.dto.PrelevementCreateDTO;
import com.prelev.apirest_springboot.dto.PrelevementJourDTO;
import com.prelev.apirest_springboot.dto.PrelevementResponseDTO;
import com.prelev.apirest_springboot.modele.Utilisateur;
import com.prelev.apirest_springboot.repository.UtilisateurRepository;
import com.prelev.apirest_springboot.security.CustomUserDetails;
import com.prelev.apirest_springboot.service.PrelevementService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public PrelevementResponseDTO create(@RequestBody PrelevementCreateDTO dto,
                                         @AuthenticationPrincipal Utilisateur utilisateur) {
        return prelevementService.create(dto, utilisateur);
    }

    @GetMapping("/lire")
    public List<PrelevementResponseDTO> read(@AuthenticationPrincipal Utilisateur utilisateur) {
        return prelevementService.read(utilisateur);
    }

    @PutMapping("/modifier/{id}")
    public PrelevementResponseDTO update(@PathVariable Long id,
                                         @RequestBody PrelevementCreateDTO dto,
                                         @AuthenticationPrincipal Utilisateur utilisateur) {
        return prelevementService.update(id, dto, utilisateur);
    }

    @DeleteMapping("/supprimer/{id}")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal Utilisateur utilisateur) {
        return prelevementService.delete(id, utilisateur);
    }

    @GetMapping("/liredate")
    public List<PrelevementJourDTO> lireDate(@AuthenticationPrincipal Utilisateur utilisateur) {
        return prelevementService.getPrelevementsAvecJour(utilisateur);
    }

    @GetMapping("/liredetail")
    public List<PrelevementJourDTO> lireDetail(@RequestParam("jour") int jour, @AuthenticationPrincipal Utilisateur utilisateur) {
        return prelevementService.getPrelevementsPourJour(utilisateur, jour);
    }

    @GetMapping("/total")
    public double getTotalPrelevements(@AuthenticationPrincipal Utilisateur utilisateur) {
        return prelevementService.getTotalPrelevements(utilisateur);
    }

    @GetMapping("/avenir")
    public List<PrelevementResponseDTO> getPrelevementsAvenir() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        Utilisateur utilisateur = userDetails.getUtilisateur();

        return prelevementService.getPrelevementsAvenir(utilisateur);
    }



    @GetMapping("/par-mois")
    public List<PrelevementCountParMoisDTO> getNombrePrelevementsParMois(@AuthenticationPrincipal Utilisateur utilisateur) {
        return prelevementService.getNombrePrelevementsParMois(utilisateur);
    }

}
