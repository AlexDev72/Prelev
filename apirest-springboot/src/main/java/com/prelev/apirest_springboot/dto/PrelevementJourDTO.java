package com.prelev.apirest_springboot.dto;

import java.math.BigDecimal;

public class PrelevementJourDTO {
    private String nom;
    private BigDecimal prix;
    private int jour;

    // Constructeur
    public PrelevementJourDTO(String nom, BigDecimal prix, int jour) {
        this.nom = nom;
        this.prix = prix;
        this.jour = jour;
    }

    // Getters et setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }

    public int getJour() { return jour; }
    public void setJour(int jour) { this.jour = jour; }
}
