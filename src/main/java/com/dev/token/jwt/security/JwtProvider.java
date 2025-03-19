package com.dev.token.jwt.security;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Date;

@Log4j2
@Component
public class JwtProvider {

    private String jwtSecret;

    private int jwtExpirations;

    public String gerarToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirations))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String getUsernameJwt(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validarJwt(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Assinatura JWT Inválida: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Token JWT Inválido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token Expirado!: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token não suportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Dados/Claims do JWT vazios: {}", e.getMessage());
        }
        return false;
    }
}
