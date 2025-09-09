package com.eterna.backend.api.rest.schemas.request.auth;

import com.eterna.backend.core.auth.application.commands.input.RefreshTokenInputCommand;

public record RefreshTokenRequest(String refreshToken) {
    public RefreshTokenInputCommand toInput() {
        return new RefreshTokenInputCommand(refreshToken);
    }
}
