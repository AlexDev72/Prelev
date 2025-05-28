package com.prelev.apirest_springboot.controller;

import com.prelev.apirest_springboot.dto.ConnexionRequeteDTO;
import com.prelev.apirest_springboot.dto.ConnexionResponseDTO;
import com.prelev.apirest_springboot.service.ConnexionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000") // à ajuster selon besoin
public class ConnexionController {

    private final ConnexionService connexionService;

    public ConnexionController(ConnexionService connexionService) {
        this.connexionService = connexionService;
    }

    @PostMapping("/connexion")
    public ResponseEntity<ConnexionResponseDTO> login(@RequestBody @Valid ConnexionRequeteDTO request) {
        ConnexionResponseDTO response = connexionService.login(request.getEmail(), request.getMdp());
        return ResponseEntity.ok(response);
    }
}
