package com.prelev.apirest_springboot.dto;

public class PrelevementParMoisDTO {
    private Long id;
    private String nom;
    private String datePrelevement; // Le jour seulement, au format texte ("30")
    private Integer prix;

    public PrelevementParMoisDTO(Long id, String nom, String datePrelevement, Integer prix) {
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

    public Integer getPrix() {
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

    public void setPrix(Integer prix) {
        this.prix = prix;
    }
}
