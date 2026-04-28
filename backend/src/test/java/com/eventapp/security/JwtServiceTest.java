package com.eventapp.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.eventapp.model.entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

class JwtServiceTest {

    private static final String SECRET_KEY = "change-this-secret-key-change-this-secret-key";

    @Test
    void generateTokenShouldIncludeUsernameAndClaims() {
        JwtService jwtService = new JwtService();
        User user = new User("user1", "test@example.com", "password", "USER");
        user.setId(42L);

        String token = jwtService.generateToken(user);

        assertEquals("user1", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, user));
    }

    @Test
    void isTokenValidShouldReturnFalseWhenUsernameDoesNotMatch() {
        JwtService jwtService = new JwtService();
        User userA = new User("userA", "a@example.com", "password", "USER");
        userA.setId(1L);
        User userB = new User("userB", "b@example.com", "password", "USER");
        userB.setId(2L);

        String token = jwtService.generateToken(userA);

        assertFalse(jwtService.isTokenValid(token, userB));
    }

    @Test
    void isTokenValidShouldReturnFalseForExpiredToken() {
        JwtService jwtService = new JwtService();
        User user = new User("expiredUser", "expired@example.com", "password", "USER");
        user.setId(99L);

        Key signingKey = Keys.hmacShaKeyFor(
            SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        String expiredToken = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis() - 10000))
                .setExpiration(new Date(System.currentTimeMillis() - 1))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();

        assertFalse(jwtService.isTokenValid(expiredToken, user));
    }
}
