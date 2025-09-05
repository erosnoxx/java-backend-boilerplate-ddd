package com.eterna.backend.infrastructure.config.security;

public class SecurityMatcher {

    public static String[] getPublicEndpoints() {
        return new String[] {
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/auth/**",
                "/users/**" // temp
        };
    }

    public static String[] getProtectedEndpoints() {
        return new String[] {
                //"/users/**",
                "/routes/**",
                "/customers/**",
                "/products/**",
                "/invoices/**",
                "/suppliers/**",
                "/orders/**"
        };
    }
}