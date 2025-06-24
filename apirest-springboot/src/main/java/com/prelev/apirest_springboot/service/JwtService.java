package com.prelev.apirest_springboot.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF".getBytes()
    );


    public String extractUsername(String token) {
        String username = extractClaim(token, Claims::getSubject);
        return username;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        T result = claimsResolver.apply(claims);
        return result;
    }

    private Claims extractAllClaims(String token) {
        if (token == null || token.isEmpty() || token.chars().filter(ch -> ch == '.').count() != 2) {
            System.out.println("[extractAllClaims] Token invalide ou mal formé : " + token);
            throw new RuntimeException("Token invalide ou mal signé");
        }
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println("[extractAllClaims] Erreur lors de l'extraction des claims : " + e.getMessage());
            throw new RuntimeException("Token invalide ou mal signé");
        }
    }


    public boolean isTokenValid(String token, String username) {
        String tokenUsername = extractUsername(token);
        boolean sameUser = tokenUsername.equalsIgnoreCase(username); // <- Utilisez equalsIgnoreCase()
        boolean expired = isTokenExpired(token);
        boolean valid = sameUser && !expired;
        return valid;
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = extractClaim(token, Claims::getExpiration);
        boolean expired = expiration.before(new Date());
        return expired;
    }

    public String generateToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        return token;
    }
}
