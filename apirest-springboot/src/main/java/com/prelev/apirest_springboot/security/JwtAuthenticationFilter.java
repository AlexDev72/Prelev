package com.prelev.apirest_springboot.security;

import com.prelev.apirest_springboot.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
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

        // 1. Extraction du token
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Décodage du JWT
        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);

        if (username != null) {
            // 3. Chargement de l'utilisateur
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 4. Validation du token ET vérification explicite du contexte
            if (jwtService.isTokenValid(jwt, userDetails)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 5. Création d'un NOUVEAU contexte (solution critique)
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);

                System.out.println("[DEBUG] Authentification établie pour: " + username);
            }
        }

        filterChain.doFilter(request, response);
    }

}
