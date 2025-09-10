package com.eterna.backend.core.auth.application.commands.input;

import java.util.UUID;

public record ChangePasswordInputCommand(UUID id, String oldPassword, String newPassword) {
}
