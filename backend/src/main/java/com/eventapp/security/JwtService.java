package com.eventapp.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.eventapp.model.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * Service for generating and validating JWT tokens.
 */
@Service
public class JwtService {

    private static final String SECRET_KEY =
            "change-this-secret-key-change-this-secret-key";

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    
    /**
     * Generate a JWT token for the given user.
     * @param user the user for whom to generate the token
     * @return the generated JWT token
     */
    public String generateToken(final User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extract the username from the token.
     * @param token the JWT token
     * @return the username contained in the token
     */
    public String extractUsername(final String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Validate the token against the user details.
     * @param token the JWT token
     * @param user the user details to compare against
     * @return true if the token is valid and belongs to the user, false otherwise
     */
    public boolean isTokenValid(final String token, final User user) {
        try {
            return extractUsername(token).equals(user.getUsername())
                    && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(final String token) {
        try {
            return extractAllClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Extract all claims from the token (all the information contained in the token).
     * @param token the JWT token
     * @return the claims contained in the token
     */
    private Claims extractAllClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Get the signing key used to sign the JWT tokens.
     * @return the signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

