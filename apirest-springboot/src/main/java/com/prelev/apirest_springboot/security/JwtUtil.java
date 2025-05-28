package com.prelev.apirest_springboot.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtUtil {

    // Clé secrète (à stocker en config ou variable d'environnement en vrai projet)
    private static final String SECRET = "token-simule-pour-demo";

    private static final long EXPIRATION_TIME = 3600_000; // 1 heure en ms

    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}