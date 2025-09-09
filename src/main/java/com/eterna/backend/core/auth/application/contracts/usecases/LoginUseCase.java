package com.eterna.backend.core.auth.application.contracts.usecases;

import com.eterna.backend.core.auth.application.commands.input.LoginInputCommand;
import com.eterna.backend.core.auth.application.commands.output.LoginOutputCommand;
import com.eterna.backend.core.shared.application.UseCase;

public interface LoginUseCase extends UseCase<LoginInputCommand, LoginOutputCommand> {
}
