package com.eterna.backend.core.auth.application.contracts.usecases;

import com.eterna.backend.core.auth.application.commands.input.ChangePasswordInputCommand;
import com.eterna.backend.core.shared.application.UseCase;

import java.util.UUID;

public interface ChangePasswordUseCase extends UseCase<ChangePasswordInputCommand, Void> {
}
