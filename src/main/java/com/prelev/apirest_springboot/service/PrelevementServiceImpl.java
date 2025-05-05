package com.prelev.apirest_springboot.service;

import com.prelev.apirest_springboot.modele.Prelevement;
import com.prelev.apirest_springboot.repository.PrelevementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PrelevementServiceImpl implements PrelevementService {

    private final PrelevementRepository prelevementRepository;

    public PrelevementServiceImpl(PrelevementRepository prelevementRepository) {
        this.prelevementRepository = prelevementRepository;
    }

    @Override
    public Prelevement create(Prelevement prelevement) {
        return prelevementRepository.save(prelevement);
    }

    @Override
    public List<Prelevement> Read() {
        return prelevementRepository.findAll();
    }

    @Override
    public Prelevement update(Long id, Prelevement prelevement) {
        return prelevementRepository.findById(id)
                .map(p -> {
                    p.setPrix(prelevement.getPrix());
                    p.setNom(prelevement.getNom());
                    p.setDate_prelevement(prelevement.getDate_prelevement());
                    return prelevementRepository.save(p);
                })
                .orElseThrow(() -> new RuntimeException("Prelevement non trouvé " + id));
    }

    @Override
    public String delete(Long id) {
        prelevementRepository.deleteById(id);
        return "Prélèvement bien supprimé";
    }
}
