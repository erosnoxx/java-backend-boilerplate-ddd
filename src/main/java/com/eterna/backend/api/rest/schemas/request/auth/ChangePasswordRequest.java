package com.eterna.backend.api.rest.schemas.request.auth;

import com.eterna.backend.core.auth.application.commands.input.ChangePasswordInputCommand;

import java.util.UUID;

public record ChangePasswordRequest(String oldPassword, String newPassword) {
    public ChangePasswordInputCommand toInput(UUID id) {
        return new ChangePasswordInputCommand(id, oldPassword, newPassword);
    }
}
