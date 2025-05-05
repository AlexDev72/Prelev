package com.prelev.apirest_springboot.repository;

import com.prelev.apirest_springboot.modele.Prelevement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrelevementRepository extends JpaRepository<Prelevement, Long> {
}
