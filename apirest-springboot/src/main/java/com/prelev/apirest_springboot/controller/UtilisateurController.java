package com.prelev.apirest_springboot.controller;

import com.prelev.apirest_springboot.dto.UtilisateurCreateDTO;
import com.prelev.apirest_springboot.dto.UtilisateurResponseDTO;
import com.prelev.apirest_springboot.service.UtilisateurService;
import com.prelev.apirest_springboot.security.JwtUtil; // Import ajouté
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "http://192.168.1.22:3000")
@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final JwtUtil jwtUtil; // Injection ajoutée

    public UtilisateurController(UtilisateurService utilisateurService, JwtUtil jwtUtil) {
        this.utilisateurService = utilisateurService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/cree")
    public UtilisateurResponseDTO create(@RequestBody @Valid UtilisateurCreateDTO dto) {
        return utilisateurService.create(dto);
    }

    @GetMapping("/lire")
    public List<UtilisateurResponseDTO> read() {
        return utilisateurService.read();
    }

    @GetMapping("/profil")
    public ResponseEntity<UtilisateurResponseDTO> getUtilisateurConnecte(
            @RequestHeader("Authorization") String token) {

        // Extraire l'ID du token JWT
        Long id = JwtUtil.extractUserId(token.replace("Bearer ", ""));
        System.out.println(id);
        // Récupérer l'utilisateur spécifique
        try {
            UtilisateurResponseDTO utilisateur = utilisateurService.findById(id);
            return ResponseEntity.ok(utilisateur);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé");
        }
    }

    @PutMapping("/modifier/{id}")
    public UtilisateurResponseDTO update(@PathVariable Long id, @RequestBody @Valid UtilisateurCreateDTO dto) {
        return utilisateurService.update(id, dto);
    }

    @DeleteMapping("/supprimer/{id}")
    public String delete(@PathVariable Long id) {
        return utilisateurService.delete(id);
    }

    @GetMapping("/verifie")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> verifierToken() {
        return ResponseEntity.ok("Token valide");
    }

}