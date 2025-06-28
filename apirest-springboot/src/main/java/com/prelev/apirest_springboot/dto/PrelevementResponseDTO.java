package com.prelev.apirest_springboot.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PrelevementResponseDTO {
    private Long id;
    private String nom;
    private LocalDate datePrelevement;
    private BigDecimal prix;

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDatePrelevement() {
        return datePrelevement;
    }

    public void setDatePrelevement(LocalDate datePrelevement) {
        this.datePrelevement = datePrelevement;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

}
