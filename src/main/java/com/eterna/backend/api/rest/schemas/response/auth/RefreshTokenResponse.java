package com.eterna.backend.api.rest.schemas.response.auth;

import com.eterna.backend.core.auth.application.commands.refreshToken.RefreshTokenOutputCommand;

import java.time.ZonedDateTime;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken,
        ZonedDateTime expiresAt) {
    public static RefreshTokenResponse of(RefreshTokenOutputCommand command) {
        return new RefreshTokenResponse(command.accessToken(), command.refreshToken(), command.expiresAt());
    }
}
