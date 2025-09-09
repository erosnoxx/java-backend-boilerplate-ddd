package com.eterna.backend.api.rest.schemas.request.auth;

import com.eterna.backend.core.auth.application.commands.input.RegisterUserInputCommand;
import com.eterna.backend.core.auth.domain.enums.Role;

public record RegisterUserRequest(String name, String email, Role role) {
    public RegisterUserInputCommand toInput() {
        return new RegisterUserInputCommand(name, email, role);
    }
}
