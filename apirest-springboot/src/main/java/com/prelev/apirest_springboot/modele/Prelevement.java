package com.prelev.apirest_springboot.modele;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "prelevement")  // nom en minuscule, sensible à ta BDD
public class Prelevement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(name = "date_prelevement")
    private Date date_prelevement;

    private Integer prix;

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

    public Date getDate_prelevement() {
        return date_prelevement;
    }

    public void setDate_prelevement(Date date_prelevement) {
        this.date_prelevement = date_prelevement;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
