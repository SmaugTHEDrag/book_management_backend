package com.example.BookManagement.config;

import com.example.BookManagement.service.auth.IAuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/*
 * Spring Security configuration class.
 * Configures JWT authentication, CORS, session management, and endpoint authorization.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final IAuthService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(IAuthService userService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userService = userService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /*
     * Configure the SecurityFilterChain.
     * - Disable CSRF since we use JWT (stateless)
     * - Configure CORS
     * - Define which endpoints are public vs. authenticated
     * - Stateless session (JWT)
     * - Add JWT filter before UsernamePasswordAuthenticationFilter
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Disable CSRF protection since JWT is used
                .csrf(AbstractHttpConfigurer::disable)
                // CORS configuration
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOriginPatterns(List.of("*")); // allow all origins
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); // allowed HTTP methods
                    config.setAllowedHeaders(List.of("*")); // allow all headers
                    config.setAllowCredentials(true); // needed for JWT or cookie-based authentication
                    return config;
                }))
                // Define endpoint authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints: login, register, fetching blogs/books, chat, Swagger docs
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/blogs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()
                        .requestMatchers("/api/chat/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        // Any other request requires authentication
                        .anyRequest().authenticated()
                )
                // Stateless session management (JWT tokens, no server-side session)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Set authentication provider (user details + password encoder)
                .authenticationProvider(authenticationProvider())
                // Add custom JWT filter before the default username/password authentication filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // encode: new BCryptPasswordEncoder().encode("123456")
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
