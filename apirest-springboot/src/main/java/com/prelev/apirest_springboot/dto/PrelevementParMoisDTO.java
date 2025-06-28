package com.prelev.apirest_springboot.dto;

import java.math.BigDecimal;

public class PrelevementParMoisDTO {
    private Long id;
    private String nom;
    private String datePrelevement; // Le jour seulement, au format texte ("30")
    private BigDecimal prix;

    public PrelevementParMoisDTO(Long id, String nom, String datePrelevement, BigDecimal prix) {
        this.id = id;
        this.nom = nom;
        this.datePrelevement = datePrelevement;
        this.prix = prix;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDatePrelevement() {
        return datePrelevement;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDatePrelevement(String datePrelevement) {
        this.datePrelevement = datePrelevement;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }
}
