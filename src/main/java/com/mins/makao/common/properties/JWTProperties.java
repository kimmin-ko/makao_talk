package com.mins.makao.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("jwt")
public class JWTProperties {
    private String secret;
    private long accessTokenLifeTime;
    private long refreshTokenLifeTime;
}