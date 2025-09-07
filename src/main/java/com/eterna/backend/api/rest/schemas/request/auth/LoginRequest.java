package com.eterna.backend.api.rest.schemas.request.auth;

import com.eterna.backend.core.auth.application.commands.login.LoginInputCommand;

public record LoginRequest(String email, String password) {
    public LoginInputCommand toInput() {
        return new LoginInputCommand(email, password);
    }
}
