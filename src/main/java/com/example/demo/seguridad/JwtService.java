package com.example.demo.seguridad;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET = "clave_super_secreta_que_debe_ser_larga_123456";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 día
                .signWith(key)
                .compact();
    }

    public String extraerEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean esValido(String token) {
        try {
            extraerEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}