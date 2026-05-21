package fr.ekod.cda.ja.cineclub.config;

import fr.ekod.cda.ja.cineclub.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(SecurityExceptionHandlers.authenticationEntryPoint())
                        .accessDeniedHandler(SecurityExceptionHandlers.accessDeniedHandler())
                )
                .authorizeHttpRequests(auth -> auth
                        // --- Règles /api/** du TP7 : INCHANGÉES ---
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/movies/**", "/api/directors/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/movies/**", "/api/directors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/movies/**", "/api/directors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/movies/**", "/api/directors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/movies/**", "/api/directors/**").hasRole("ADMIN")
                        // --- Pages web publiques ---
                        .requestMatchers("/", "/css/**", "/register", "/login", "/logout",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/favicon.ico",
                                "/webjars/**"
                        ).permitAll()
                        // --- Pages web réservées aux ADMIN (règle la plus spécifique EN PREMIER) ---
                        .requestMatchers("/movies/new").hasRole("ADMIN")
                        // --- Pages web protégées : nécessitent d'être connecté ---
                        .requestMatchers("/movies/**", "/directors/**").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
