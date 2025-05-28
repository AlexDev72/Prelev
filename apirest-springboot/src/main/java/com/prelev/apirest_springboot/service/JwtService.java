package com.prelev.apirest_springboot.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

/**
 * Service pour la gestion des tokens JWT :
 * - extraction des informations (claims) depuis un token JWT
 * - validation du token (signature, expiration)
 * - génération de nouveaux tokens JWT
 */
@Service
public class JwtService {

    // Clé secrète utilisée pour signer et valider les JWT
    private static final String SECRET_KEY = "token-simule-pour-demo";

    /**
     * Extrait le nom d'utilisateur (le sujet) du token JWT.
     * @param token JWT sous forme de chaîne
     * @return le nom d'utilisateur contenu dans le token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait une réclamation (claim) spécifique depuis un token JWT
     * en appliquant la fonction claimsResolver sur l'objet Claims.
     * @param token JWT
     * @param claimsResolver fonction pour extraire une donnée depuis les claims
     * @param <T> type de la donnée extraite
     * @return la donnée extraite de type T
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrait tous les claims (données contenues) dans le token JWT.
     * Vérifie la signature du token avec la clé secrète.
     *
     * @param token JWT à parser
     * @return Claims extraits du token
     * @throws RuntimeException en cas de signature invalide
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY) // clé en String, sans conversion en bytes ici
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature");
        }
    }

    /**
     * Vérifie si un token est valide pour un nom d'utilisateur donné :
     * - le nom d'utilisateur dans le token doit correspondre
     * - le token ne doit pas être expiré
     *
     * @param token JWT
     * @param username nom d'utilisateur à vérifier
     * @return true si le token est valide, false sinon
     */
    public boolean isTokenValid(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * Vérifie si le token JWT est expiré.
     *
     * @param token JWT
     * @return true si expiré, false sinon
     */
    private boolean isTokenExpired(String token) {
        final Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    /**
     * Génère un nouveau token JWT pour un nom d'utilisateur donné,
     * avec une durée de validité de 10 heures.
     *
     * @param username nom d'utilisateur à inclure dans le token
     * @return token JWT signé
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 heures
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes()) // ici conversion en bytes pour la signature
                .compact();
    }
}
