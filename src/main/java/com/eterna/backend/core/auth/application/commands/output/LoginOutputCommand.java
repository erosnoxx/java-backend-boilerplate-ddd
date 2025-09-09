package com.eterna.backend.core.auth.application.commands.output;

import com.eterna.backend.core.auth.application.commands.TokenPair;

import java.time.ZonedDateTime;

public record LoginOutputCommand(
        String accessToken,
        String refreshToken,
        ZonedDateTime expiresAt) {

    public static LoginOutputCommand of(TokenPair pair) {
        return new LoginOutputCommand(pair.accessToken(), pair.refreshToken(), pair.expiresAt());
    }
}
