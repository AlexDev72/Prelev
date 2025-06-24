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

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Pas de token Bearer, continuer la chaîne sans authentification
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            // Essayer d'extraire le username du token
            username = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            // Log l'erreur pour debug
            System.out.println("[JwtAuthenticationFilter] Token invalide ou mal formé : " + e.getMessage());
            // Ne pas authentifier, juste passer la requête
            filterChain.doFilter(request, response);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
