package com.eterna.backend.api.rest.schemas.request.auth;

import com.eterna.backend.core.auth.application.commands.input.ActivateUserInputCommand;

import java.util.UUID;

public record ActivateUserRequest(String otpCode) {
    public ActivateUserInputCommand toInput(UUID userId) {
        return new ActivateUserInputCommand(otpCode, userId);
    }
}
