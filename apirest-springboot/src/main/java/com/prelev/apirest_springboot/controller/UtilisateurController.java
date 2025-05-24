package com.prelev.apirest_springboot.controller;

import com.prelev.apirest_springboot.dto.UtilisateurCreateDTO;
import com.prelev.apirest_springboot.dto.UtilisateurResponseDTO;
import com.prelev.apirest_springboot.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/cree")
    public UtilisateurResponseDTO create(@RequestBody @Valid UtilisateurCreateDTO dto) {
        return utilisateurService.create(dto);
    }

    @GetMapping("/lire")
    public List<UtilisateurResponseDTO> read() {
        return utilisateurService.read();
    }

    @PutMapping("/modifier/{id}")
    public UtilisateurResponseDTO update(@PathVariable Long id, @RequestBody @Valid UtilisateurCreateDTO dto) {
        return utilisateurService.update(id, dto);
    }

    @DeleteMapping("/supprimer/{id}")
    public String delete(@PathVariable Long id) {
        return utilisateurService.delete(id);
    }
}
