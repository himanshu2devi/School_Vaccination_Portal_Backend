package com.himanshu.StudentService.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "schoolvaccinationdrivehimanshu";

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token)
    {
        return extractClaims(token).getSubject();
    }

    public String getRole(String token) {
        return (String) extractClaims(token).get("role");
    }
}
