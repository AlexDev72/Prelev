package com.prelev.apirest_springboot.controller;

import com.prelev.apirest_springboot.dto.PrelevementCreateDTO;
import com.prelev.apirest_springboot.dto.PrelevementResponseDTO;
import com.prelev.apirest_springboot.modele.Utilisateur;
import com.prelev.apirest_springboot.service.PrelevementService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/prelevement")
public class PrelevementController {

    private final PrelevementService prelevementService;

    public PrelevementController(PrelevementService prelevementService) {
        this.prelevementService = prelevementService;
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
}
