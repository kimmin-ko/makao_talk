package com.mins.makao.common.security;

public class Tokens {

    public enum Type{
        ACCESS, REFRESH;
    }

    private String accessToken;
    private String refreshToken;

}
