package com.prelev.apirest_springboot.modele;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;




@Entity
@Table(name = "Prelevement")
public class Prelevement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Date date_prelevement;
    private Integer prix;

    public Date getDate_prelevement() {
        return date_prelevement;
    }

    public void setDate_prelevement(Date date_prelevement) {
        this.date_prelevement = date_prelevement;
    }

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

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }
}
