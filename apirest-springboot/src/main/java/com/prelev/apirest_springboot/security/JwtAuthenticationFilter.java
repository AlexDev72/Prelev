package com.prelev.apirest_springboot.security;

import com.prelev.apirest_springboot.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtre d'authentification JWT qui intercepte chaque requête HTTP pour :
 *  - extraire le token JWT de l'en-tête Authorization
 *  - valider le token
 *  - authentifier l'utilisateur dans le contexte Spring Security
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Constructeur injectant les dépendances nécessaires : service JWT et service utilisateur.
     */
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Filtre exécuté à chaque requête HTTP.
     *
     * 1. Récupère l'en-tête Authorization.
     * 2. Vérifie la présence d'un token Bearer.
     * 3. Extrait le nom d'utilisateur du token JWT.
     * 4. Charge les détails utilisateur via UserDetailsService.
     * 5. Vérifie la validité du token.
     * 6. Si valide, configure l'authentification dans le contexte Spring Security.
     * 7. Passe la requête au filtre suivant.
     *
     * @param request   Requête HTTP entrante
     * @param response  Réponse HTTP
     * @param filterChain Chaîne des filtres
     * @throws ServletException en cas d'erreur de filtrage
     * @throws IOException en cas d'erreur d'entrée/sortie
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Récupération de l'en-tête Authorization
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Si absence de token Bearer, poursuivre sans authentification
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraction du token JWT
        jwt = authHeader.substring(7);
        // Extraction du nom d'utilisateur depuis le token
        username = jwtService.extractUsername(jwt);

        // Vérification que l'utilisateur n'est pas déjà authentifié dans le contexte Spring
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Chargement des détails utilisateur
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Validation du token JWT (signature, expiration, etc.)
            if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
                // Création de l'objet d'authentification Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Ajout des détails de la requête HTTP (IP, session, etc.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Placement de l'authentification dans le contexte Spring Security
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Passage au filtre suivant dans la chaîne
        filterChain.doFilter(request, response);
    }
}
