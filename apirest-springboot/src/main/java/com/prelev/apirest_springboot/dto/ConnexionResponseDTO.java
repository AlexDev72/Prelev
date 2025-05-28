package com.prelev.apirest_springboot.dto;

public class ConnexionResponseDTO {
    private String token;
    private Long utilisateurId;  // ou int selon ton type

    // Constructeurs, getters, setters

    public ConnexionResponseDTO(String token, Long utilisateurId) {
        this.token = token;
        this.utilisateurId = utilisateurId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
}

