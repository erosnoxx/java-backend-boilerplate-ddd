package com.eterna.backend.core.auth.application.commands;

import java.time.ZonedDateTime;

public record TokenPair(
        String accessToken,
        String refreshToken,
        ZonedDateTime expiresAt) {
}
