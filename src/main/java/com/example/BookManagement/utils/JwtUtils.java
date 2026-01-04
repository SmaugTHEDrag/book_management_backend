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

// jwt helper used by spring security filter
public class JwtUtils {

    private static final long EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L;

    // key length is required for HS512, currently hardcoded
    private static final String SECRET = "ThisIsASecretKeyForJwtThatIsAtLeastSixtyFourCharactersLong123456!";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // generate a JWT for the authenticated user
    public static String generateJwt(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        // put roles into token for later authorization
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // build the JWT
        return Jwts.builder()
                .setSubject(user.getUsername())  // username
                .claim("roles", roles)  // role
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))  // expiration time
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512) // Sign with HS512
                .compact();
    }

    public static boolean validateJwt(String jwt) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwt);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    // Extract username from JWT token.
    public static String getUsername(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return claims.getSubject();
    }
}
