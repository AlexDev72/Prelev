package com.prelev.apirest_springboot.security;

import com.prelev.apirest_springboot.modele.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

import io.jsonwebtoken.security.Keys;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF".getBytes()
    );


    private static final long EXPIRATION_TIME = 86400000; // 24 heures en ms

    public static String generateToken(Utilisateur user) { // Prend un User entier en paramètre
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId()) // Ajoute l'ID comme claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
    // Méthode pour extraire l'ID utilisateur du token
    public static Long extractUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        // Si vous stockez l'ID dans les claims
        if (claims.get("userId") != null) {
            return Long.parseLong(claims.get("userId").toString());
        }
        String email = claims.getSubject();

        throw new IllegalArgumentException("Token ne contient pas d'ID utilisateur");
    }

}