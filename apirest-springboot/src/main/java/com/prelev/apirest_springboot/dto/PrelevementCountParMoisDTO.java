package com.prelev.apirest_springboot.dto;

public class PrelevementCountParMoisDTO {
    private int annee;
    private int mois;
    private long nombre;

    public PrelevementCountParMoisDTO(int annee, int mois, long nombre) {
        this.annee = annee;
        this.mois = mois;
        this.nombre = nombre;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public long getNombre() {
        return nombre;
    }

    public void setNombre(long nombre) {
        this.nombre = nombre;
    }
// getters et setters
}
