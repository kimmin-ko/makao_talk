package com.mins.makao.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JWTTokenTest {

    static final Algorithm algorithm = Algorithm.HMAC512("test-secret");

    @Test
    @DisplayName("1. JWT Token 생성 하기")
    void jwt_create() throws InterruptedException {
        // when
        String token = JWT.create()
                .withSubject("1")
                .withClaim("exp", Instant.now().getEpochSecond() + 1)
                .withIssuedAt(new Date())
                .withIssuer("min")
                .sign(algorithm);

        // then
        assertThat(token).isNotNull();

        Thread.sleep(1500);
        assertThatThrownBy(() -> JWT.require(algorithm).build().verify(token))
                .isInstanceOf(TokenExpiredException.class);
    }

    private void printClaim(String key, Claim claim) {
        if (claim.isNull()) {
            System.out.printf("%s:{nll}%s\n", key, "Claim is null.");
        } else if (claim.asBoolean() != null) {
            System.out.printf("%s:{bol}%b\n", key, claim.asBoolean());
        } else if (claim.asLong() != null) {
            System.out.printf("%s:{lng}%d\n", key, claim.asLong());
        } else if (claim.asString() != null) {
            System.out.printf("%s:{str}%s\n", key, claim.asString());
        } else if (claim.asDate() != null) {
            System.out.printf("%s:{dte}%s\n", key, claim.asDate().toString());
        } else if (claim.asInt() != null) {
            System.out.printf("%s:{int}%d\n", key, claim.asInt());
        } else if (claim.asDouble() != null) {
            System.out.printf("%s:{dbl}%f\n", key, claim.asDouble());
        } else if (claim.asArray(String.class) != null) {
            String[] claims = claim.asArray(String.class);
            System.out.printf("%s:{arr}%s\n", key, Arrays.stream(claims).collect(toList()));
        } else if (claim.asMap() != null) {
            System.out.printf("%s:{map}%s\n", key, claim.asMap());
        }
    }

}