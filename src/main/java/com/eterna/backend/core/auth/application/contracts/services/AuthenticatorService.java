package com.eterna.backend.core.auth.application.contracts.services;

import com.eterna.backend.core.auth.application.commands.TokenPair;

public interface AuthenticatorService {
    TokenPair getToken(String email, String password);
}
