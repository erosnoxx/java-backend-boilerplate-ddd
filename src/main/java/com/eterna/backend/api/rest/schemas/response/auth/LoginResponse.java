package com.eterna.backend.api.rest.schemas.response.auth;

import com.eterna.backend.core.auth.application.commands.output.LoginOutputCommand;

import java.time.ZonedDateTime;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        ZonedDateTime expiresAt) {
    public static LoginResponse of(LoginOutputCommand command) {
        return new LoginResponse(command.accessToken(), command.refreshToken(), command.expiresAt());
    }
}
