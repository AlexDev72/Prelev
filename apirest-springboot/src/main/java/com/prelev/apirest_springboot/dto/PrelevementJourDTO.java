package com.prelev.apirest_springboot.dto;

public class PrelevementJourDTO {
    private String nom;
    private Integer prix;
    private int jour;

    // Constructeur
    public PrelevementJourDTO(String nom, int prix, int jour) {
        this.nom = nom;
        this.prix = prix;
        this.jour = jour;
    }

    // Getters et setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Integer getPrix() { return prix; }
    public void setPrix(Integer prix) { this.prix = prix; }

    public int getJour() { return jour; }
    public void setJour(int jour) { this.jour = jour; }
}
