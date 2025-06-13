package com.prelev.apirest_springboot.repository;

import com.prelev.apirest_springboot.modele.Prelevement;
import com.prelev.apirest_springboot.modele.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrelevementRepository extends JpaRepository<Prelevement, Long> {
    List<Prelevement> findByUtilisateur(Utilisateur utilisateur);
    List<Prelevement> findByUtilisateur_Id(Long utilisateurId);
}
