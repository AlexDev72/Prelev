package com.prelev.apirest_springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PrelevementCreateDTO {
    private String nom;
    private LocalDate datePrelevement;
    private BigDecimal prix;

    // Getters & Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    @JsonFormat(pattern = "yyyy-MM-dd")
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
