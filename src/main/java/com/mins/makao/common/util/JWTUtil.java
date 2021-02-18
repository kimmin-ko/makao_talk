package com.mins.makao.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mins.makao.common.properties.JWTProperties;
import com.mins.makao.common.security.Tokens;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

@Component
public class JWTUtil {
    public static final String AUTH_HEADER = "Authentication";
    public static final String REFRESH_HEADER = "refresh-token";
    public static final String BEARER = "Bearer ";

    @Getter
    private final JWTProperties properties;
    private final Algorithm algorithm;

    public JWTUtil(JWTProperties properties) {
        this.properties = properties;
        this.algorithm = Algorithm.HMAC512(properties.getSecret());
    }

    public String generate(Long userId, Tokens.Type tokenType) {
        Objects.requireNonNull(userId, "user id is required.");

        long now = Instant.now().getEpochSecond();

        return JWT.create()
                .withSubject(userId.toString()) // sub
                .withClaim("exp", now + getLifeTime(tokenType)) // exp
                .withClaim("iss", "min")
                .withClaim("iat", now)
                .sign(algorithm);
    }

    private long getLifeTime(Tokens.Type tokenType) {
        if (Objects.isNull(tokenType))
            return properties.getAccessTokenLifeTime();

        switch (tokenType) {
            case REFRESH:
                return properties.getRefreshTokenLifeTime();
            case ACCESS:
            default:
                return properties.getAccessTokenLifeTime();
        }
    }
}
