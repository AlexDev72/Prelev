package com.prelev.apirest_springboot.dto;

import java.time.LocalDate;

public class PrelevementResponseDTO {
    private Long id;
    private String nom;
    private LocalDate datePrelevement;
    private Integer prix;

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

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public static class PrelevementJourDTO {
        private String nom;
        private Double prix;
        private int jour;

        // Constructeur
        public PrelevementJourDTO(String nom, Double prix, int jour) {
            this.nom = nom;
            this.prix = prix;
            this.jour = jour;
        }

        // Getters et setters
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }

        public Double getPrix() { return prix; }
        public void setPrix(Double prix) { this.prix = prix; }

        public int getJour() { return jour; }
        public void setJour(int jour) { this.jour = jour; }
    }
}
