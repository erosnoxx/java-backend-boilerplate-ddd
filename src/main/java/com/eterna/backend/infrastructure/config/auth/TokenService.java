package com.eterna.backend.infrastructure.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.eterna.backend.core.auth.application.commands.TokenPair;
import com.eterna.backend.infrastructure.persistence.entities.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String SECRET;
    @Value("${api.security.token.expirationTimeInHours}")
    private int ACCESS_TOKEN_HOURS;
    @Value("${api.security.token.refreshExpirationTimeInHours}")
    private int REFRESH_TOKEN_HOURS;

    public TokenPair generateToken(UserEntity user) {
        try {
            var algorithm = Algorithm.HMAC256(SECRET);

            var accessExpiresAt = genExpirationDate(ACCESS_TOKEN_HOURS);
            var refreshExpiresAt = genExpirationDate(REFRESH_TOKEN_HOURS);

            var accessToken = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(accessExpiresAt)
                    .sign(algorithm);

            var refreshToken = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(refreshExpiresAt)
                    .sign(algorithm);

            return new TokenPair(accessToken, refreshToken, getExpiresAtLocal(accessExpiresAt));
        } catch(JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            var algorithm = Algorithm.HMAC256(SECRET);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant genExpirationDate(long hours){
        return Instant.now().plusSeconds(hours * 3600);
    }

    public ZonedDateTime getExpiresAtLocal(Instant expiresAt) {
        return expiresAt.atZone(ZoneId.of("America/Sao_Paulo"));
    }
}
