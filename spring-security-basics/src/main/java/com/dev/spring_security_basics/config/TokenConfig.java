package com.dev.spring_security_basics.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dev.spring_security_basics.entity.Operation;
import com.dev.spring_security_basics.entity.Role;
import com.dev.spring_security_basics.entity.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenConfig {
    private final String secret = "secret";

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim("userId", user.getId())
                .withClaim("roles", user.getRoles().stream()
                        .map(Role::getId)
                        .toList())
                .withClaim("operations", user.getRoles().stream()
                        .flatMap(r -> r.getAllowedOperations().stream())
                        .map(opr-> ((Operation)opr).getId())
                        .toList())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // validando o token: assinatura, tempo de expiração e formato.
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build().verify(token);

            return Optional.of(
                    JWTUserData.builder()
                            .userId(decodedJWT.getClaim("userId").asLong())
                            .email(decodedJWT.getSubject())
                            .roles(decodedJWT.getClaim("roles").asList(String.class))
                            .operations(decodedJWT.getClaim("operations").asList(String.class))
                            .build()
            );
        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }
    }
}
