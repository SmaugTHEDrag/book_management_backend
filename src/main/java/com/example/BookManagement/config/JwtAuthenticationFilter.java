package com.example.BookManagement.config;

import com.example.BookManagement.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
 * JWT Authentication Filter.
 * Checks the Authorization header for a JWT token.
 * If valid, sets authentication in SecurityContext.
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    // Main filter method executed for each HTTP request.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extract JWT from Authorization header
        String jwt = getJwtFromHeader(request);

        // Validate JWT
        if (jwt != null && JwtUtils.validateJwt(jwt)) {
            // Extract username from JWT
            String username = JwtUtils.getUsername(jwt);

            // Load user details using the username
            // Note: Cast assumes your IAuthService returns Spring Security UserDetails
            User user = (User) userDetailsService.loadUserByUsername(username);

            // Create authentication token with authorities
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user,   // Principal
                            null,      // Credentials are not needed here
                            user.getAuthorities() // Roles / authorities
                    );
            // Set the authentication in Spring Security context
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Continue filter chain
        filterChain.doFilter(request, response);
    }

    // Extract JWT token from the Authorization header.
    // Header should be in the format: "Authorization: Bearer <token>"
    private String getJwtFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        // Check if header is present and starts with "Bearer "
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7).trim(); // remove "Bearer " prefix (7 characters)
        }
        return null; // No JWT found
    }
}
