package com.example.callcenter1.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String jwtSecret = "callcenter1supersecretkeyforjwtmustbelongenough"; // JWT token'ının şifreleme anahtarı, doğru oluşan token'ın doğrulanması için gereklidir.
    private final long jwtExpirationMs = 86400000; // 1 gün

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
    /* Ne yapar: String olan secret key'i şifreleme anahtarına çevirir.
Keys.hmacShaKeyFor(): HMAC-SHA algoritması için anahtar oluşturur.
getBytes(): String'i byte array'e çevirir.
Amaç: JWT imzalama işlemleri için güvenli anahtar sağlar.*/

    public String generateToken(String operatorName, String roleType) {
        return Jwts.builder()
                .setSubject(operatorName) // JWT token'ının içindeki operatorName'i belirler.
                .claim("role", roleType) // JWT token'ının içindeki role'u belirler.
                .setIssuedAt(new Date()) // JWT token'ının oluşturulma tarihini belirler.
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // JWT token'ının süresini belirler.
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // JWT token'ının şifreleme algoritmasını belirler.
                .compact(); // JWT token'ını oluşturur.
    }

    public String getOperatorNameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build() // JWT token'ının doğrulanması için gereklidir.
                .parseClaimsJws(token).getBody().getSubject(); // JWT token'ının içindeki operatorName'i döndürür.
    }

   /* public String getRoleFromToken(String token) {
        return (String) Jwts.parserBuilder().setSigningKey(getSigningKey()).build()// JWT token'ının doğrulanması için gereklidir.
                .parseClaimsJws(token).getBody().get("role"); // JWT token'ının içindeki role'u döndürür.
    }*/

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token); // JWT tokenının geçerli olup olmadığını kontrol eder.
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
} 