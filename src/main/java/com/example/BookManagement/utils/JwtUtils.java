package com.example.BookManagement.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Utility class for generating, validating, and reading JWT tokens.
 */
public class JwtUtils {
    // Token expiration time: 30 days (in milliseconds)
    private static final long EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L;

    // Secret key for signing the JWT (must be at least 64 characters for HS512)
    private static final String SECRET = "ThisIsASecretKeyForJwtThatIsAtLeastSixtyFourCharactersLong123456!";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Generate a JWT for the authenticated user
    public static String generateJwt(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        // Extract roles from the user's authorities
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Build the JWT
        return Jwts.builder()
                .setSubject(user.getUsername())  // Store username as the subject
                .claim("roles", roles)  // Add roles to the JWT payload
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))  // Set expiration time
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512) // Sign with HS512 algorithm
                .compact();
    }

    // Validate a JWT token
    public static boolean validateJwt(String jwt) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)  // Set the signing key for validation
                    .build()
                    .parseClaimsJws(jwt);      // Parse and validate
            return true;
        } catch (Exception exception) {
            exception.printStackTrace(); // Log the error
        }
        return false;
    }

    // Extract username from JWT token.
    public static String getUsername(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)  // Set the signing key
                .build()
                .parseClaimsJws(jwt)   // Parse token
                .getBody();  // Get payload
        return claims.getSubject();   // Extract username
    }
}
