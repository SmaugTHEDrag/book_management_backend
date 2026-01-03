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

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Get JWT token from Authorization header
        String jwt = getJwtFromHeader(request);

        // Check if token is present and valid
        if (jwt != null && JwtUtils.validateJwt(jwt)) {

            // Extract username from JWT
            String username = JwtUtils.getUsername(jwt);

            // Load user details from database
            User user = (User) userDetailsService.loadUserByUsername(username);

            // Create authentication object with user details and authorities
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user, // principal
                            null, // credentials not needed here
                            user.getAuthorities() // roles/authorities
                    );
            // Set authentication in Spring Security context
            // Only needed if not already authenticated
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    // Bearer token from Authorization header
    private String getJwtFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        // Check if header is present and properly formatted
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7).trim();
        }
        return null;
    }
}
