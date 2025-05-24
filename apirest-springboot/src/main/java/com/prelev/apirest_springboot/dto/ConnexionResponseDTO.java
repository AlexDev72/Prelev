// AuthResponseDTO.java
package com.prelev.apirest_springboot.dto;

public class ConnexionResponseDTO {
    private String token;  // ici tu peux mettre un JWT ou un message

    public ConnexionResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
