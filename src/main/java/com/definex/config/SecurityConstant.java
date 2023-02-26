package com.definex.config;

public final class SecurityConstant {

    private SecurityConstant(){}

    public static final String SECRET_KEY = "WORD_APP";
    public static final int CURRENT_EXPIRED_DAY = 5;
    public static final String AUTHORIZATION_TOKEN_PREFIX = "Bearer ";

    public static final String COOKIE_TOKEN = "token";
}
