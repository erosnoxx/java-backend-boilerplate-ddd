package com.eterna.backend.core.auth.application.commands.input;

import java.util.UUID;

public record ActivateUserInputCommand(String otpCode, UUID userId) {
}
