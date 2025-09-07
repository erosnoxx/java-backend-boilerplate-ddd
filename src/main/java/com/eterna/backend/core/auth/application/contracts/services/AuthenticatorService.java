package com.eterna.backend.core.auth.application.contracts.services;

import com.eterna.backend.core.auth.application.commands.TokenPair;

import java.util.UUID;

public interface AuthenticatorService {
    TokenPair getToken(String email, String password);
    TokenPair renewToken(String refreshToken);
}
