package com.prelev.apirest_springboot.config;

import com.prelev.apirest_springboot.security.JwtAuthenticationFilter;
import com.prelev.apirest_springboot.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(HttpMethod.POST, "/auth/connexion").permitAll()
                                .requestMatchers(HttpMethod.POST, "/utilisateur/cree").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/utilisateur/modifier/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/utilisateur/profil").authenticated()
                                .requestMatchers(HttpMethod.POST, "/prelevement/cree").authenticated()
                                .requestMatchers("/prelevement/liredate").authenticated()
                                .requestMatchers("/prelevement/liredetail").authenticated()
                                .requestMatchers("/prelevement/total").authenticated()
                                .requestMatchers("/prelevement/avenir").authenticated()
                                .requestMatchers("/prelevement/par-mois").authenticated()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // Ajoutez la gestion des exceptions
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                        }));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Autorise origine frontend
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://192.168.1.22:3000"));

        // Autorise les méthodes nécessaires
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Autorise les headers nécessaires (y compris Authorization)
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With", "withcredentials"));

        // Autorise les cookies/credentials
        configuration.setAllowCredentials(true);

        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
