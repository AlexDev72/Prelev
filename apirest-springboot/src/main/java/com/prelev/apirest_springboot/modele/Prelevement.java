package com.prelev.apirest_springboot.modele;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "prelevement")  // nom en minuscule, sensible à ta BDD
public class Prelevement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(name = "date_prelevement")
    private LocalDate date_prelevement;

    @Column(name = "date_fin")
    private LocalDate date_fin;

    @Column(precision = 10, scale = 2) // 10 chiffres au total, 2 après la virgule
    private BigDecimal prix; // Changé de Integer à BigDecimal

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

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

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public LocalDate getDate_prelevement() {
        return date_prelevement;
    }

    public void setDate_prelevement(LocalDate date_prelevement) {
        this.date_prelevement = date_prelevement;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
