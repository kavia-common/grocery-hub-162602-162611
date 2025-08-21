package com.example.grocerybackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utility class for creating and validating JWT tokens.
 */
@Component
public class JwtUtil {

    @Value("${app.security.jwt.secret}")
    private String jwtSecret;

    @Value("${app.security.jwt.expiration-ms}")
    private long jwtExpirationMs;

    private Key getSigningKey() {
        // Ensure the secret is at least 256 bits for HS256
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // PUBLIC_INTERFACE
    public String generateToken(String subject) {
        /** Generates a JWT for the given subject (typically the user email). */
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // PUBLIC_INTERFACE
    public String getSubject(String token) {
        /** Extracts the subject (email) from a token. */
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // PUBLIC_INTERFACE
    public boolean validate(String token) {
        /** Validates a token signature and expiration. */
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }
}
