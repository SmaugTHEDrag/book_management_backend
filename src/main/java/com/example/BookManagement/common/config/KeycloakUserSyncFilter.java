package com.example.BookManagement.common.config;

import com.example.BookManagement.user.entity.User;
import com.example.BookManagement.user.repository.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class KeycloakUserSyncFilter extends OncePerRequestFilter {

    private final IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            syncUser(jwt);
        }

        filterChain.doFilter(request, response);
    }

    private void syncUser(Jwt jwt) {
        String keycloakUserId = jwt.getSubject(); // Claim 'sub' trong token
        String email = jwt.getClaimAsString("email");
        String username = jwt.getClaimAsString("preferred_username");
        String fullName = jwt.getClaimAsString("name");

        if (username == null) {
            username = email;
        }

        User user = userRepository.findByKeycloakUserId(keycloakUserId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setKeycloakUserId(keycloakUserId);
                    newUser.setEmail(email != null ? email : username + "@keycloak.local");
                    newUser.setUsername(username);
                    newUser.setFullName(fullName);
                    newUser.setStatus("ACTIVE");
                    newUser.setCreatedAt(OffsetDateTime.now());
                    return newUser;
                });

        user.setLastLoginAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        if (fullName != null) {
            user.setFullName(fullName);
        }

        userRepository.save(user);
    }
}