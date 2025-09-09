package com.eterna.backend.core.auth.application.usecases;

import com.eterna.backend.core.auth.application.commands.input.RefreshTokenInputCommand;
import com.eterna.backend.core.auth.application.commands.output.RefreshTokenOutputCommand;
import com.eterna.backend.core.auth.application.contracts.services.AuthenticatorService;
import com.eterna.backend.core.auth.application.contracts.usecases.RefreshAccessTokenUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshAccessTokenUseCaseImpl implements RefreshAccessTokenUseCase {
    @Autowired
    private AuthenticatorService authenticatorService;

    @Override
    public RefreshTokenOutputCommand execute(RefreshTokenInputCommand command) {
        return RefreshTokenOutputCommand.of(
                authenticatorService.renewToken(command.refreshToken()));
    }
}
