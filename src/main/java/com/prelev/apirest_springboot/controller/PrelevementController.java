package com.prelev.apirest_springboot.controller;

import com.prelev.apirest_springboot.dto.PrelevementCreateDTO;
import com.prelev.apirest_springboot.dto.PrelevementResponseDTO;
import com.prelev.apirest_springboot.service.PrelevementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prelevement")
public class PrelevementController {

    private final PrelevementService prelevementService;

    public PrelevementController(PrelevementService prelevementService) {
        this.prelevementService = prelevementService;
    }

    @PostMapping("/cree")
    public PrelevementResponseDTO create(@RequestBody PrelevementCreateDTO dto) {
        return prelevementService.create(dto);
    }

    @GetMapping("/lire")
    public List<PrelevementResponseDTO> read() {
        return prelevementService.read();
    }

    @PutMapping("/modifier/{id}")
    public PrelevementResponseDTO update(@PathVariable Long id, @RequestBody PrelevementCreateDTO dto) {
        return prelevementService.update(id, dto);
    }

    @DeleteMapping("/supprimer/{id}")
    public String delete(@PathVariable Long id) {
        return prelevementService.delete(id);
    }
}
