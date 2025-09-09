package com.eterna.backend.core.auth.application.commands.input;

import com.eterna.backend.core.auth.domain.enums.Role;

public record RegisterUserInputCommand(
        String name,
        String email,
        Role role) {
}
