package com.eterna.backend.core.auth.application.commands.output;

import com.eterna.backend.core.auth.application.commands.TokenPair;

import java.time.ZonedDateTime;

public record RefreshTokenOutputCommand(
        String accessToken,
        String refreshToken,
        ZonedDateTime expiresAt) {

    public static RefreshTokenOutputCommand of(TokenPair pair) {
        return new RefreshTokenOutputCommand(pair.accessToken(), pair.refreshToken(), pair.expiresAt());
    }
}
