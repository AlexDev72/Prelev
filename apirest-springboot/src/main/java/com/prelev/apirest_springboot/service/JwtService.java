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
        System.out.println("[extractUsername] Token reçu : " + token);
        String username = extractClaim(token, Claims::getSubject);
        System.out.println("[extractUsername] Nom d'utilisateur extrait : " + username);
        return username;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        System.out.println("[extractClaim] Extraction d'un claim depuis le token");
        final Claims claims = extractAllClaims(token);
        T result = claimsResolver.apply(claims);
        System.out.println("[extractClaim] Claim extrait : " + result);
        return result;
    }

    private Claims extractAllClaims(String token) {
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
        System.out.println("[isTokenValid] Vérification du token pour l'utilisateur : " + username);
        String tokenUsername = extractUsername(token);
        boolean sameUser = tokenUsername.equalsIgnoreCase(username); // <- Utilisez equalsIgnoreCase()
        boolean expired = isTokenExpired(token);
        boolean valid = sameUser && !expired;
        System.out.println("[isTokenValid] Nom d'utilisateur dans le token : " + tokenUsername);
        System.out.println("[isTokenValid] Correspondance utilisateur : " + sameUser);
        System.out.println("[isTokenValid] Token expiré : " + expired);
        System.out.println("[isTokenValid] Token valide : " + valid);
        return valid;
    }

    private boolean isTokenExpired(String token) {
        System.out.println("[isTokenExpired] Vérification de l'expiration du token");
        final Date expiration = extractClaim(token, Claims::getExpiration);
        boolean expired = expiration.before(new Date());
        System.out.println("[isTokenExpired] Date d'expiration : " + expiration);
        System.out.println("[isTokenExpired] Est expiré ? : " + expired);
        return expired;
    }

    public String generateToken(String username) {
        System.out.println("[generateToken] Génération d'un token pour l'utilisateur : " + username);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        System.out.println("[generateToken] Token généré : " + token);
        return token;
    }
}
