package com.eterna.backend.core.auth.application.contracts.usecases;

import com.eterna.backend.core.auth.application.commands.refreshToken.RefreshTokenInputCommand;
import com.eterna.backend.core.auth.application.commands.refreshToken.RefreshTokenOutputCommand;
import com.eterna.backend.core.shared.application.UseCase;

public interface RefreshAccessTokenUseCase extends UseCase<RefreshTokenInputCommand, RefreshTokenOutputCommand> {
}
