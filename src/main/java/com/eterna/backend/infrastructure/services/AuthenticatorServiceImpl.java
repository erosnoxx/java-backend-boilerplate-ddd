package com.eterna.backend.infrastructure.services;

import com.eterna.backend.core.auth.application.commands.TokenPair;
import com.eterna.backend.core.auth.application.contracts.services.AuthenticatorService;
import com.eterna.backend.infrastructure.config.auth.TokenService;
import com.eterna.backend.infrastructure.persistence.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatorServiceImpl implements AuthenticatorService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authManager;

    @Override
    public TokenPair getToken(String email, String password) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                email, password);

        var auth = authManager.authenticate(usernamePassword);

        return tokenService
                .generateToken((UserEntity) auth.getPrincipal());
    }
}
