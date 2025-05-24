// AuthRequestDTO.java
package com.prelev.apirest_springboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ConnexionRequeteDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String mdp;  // nom du champ mot de passe dans ta base

    // getters et setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMdp() { return mdp; }
    public void setMdp(String mdp) { this.mdp = mdp; }
}
