package com.eterna.backend.core.auth.application.contracts.usecases;

import com.eterna.backend.core.auth.application.commands.input.RefreshTokenInputCommand;
import com.eterna.backend.core.auth.application.commands.output.RefreshTokenOutputCommand;
import com.eterna.backend.core.shared.application.UseCase;

public interface RefreshAccessTokenUseCase extends UseCase<RefreshTokenInputCommand, RefreshTokenOutputCommand> {
}
