package com.eterna.backend.core.auth.application.contracts.usecases;

import com.eterna.backend.core.auth.application.commands.login.LoginInputCommand;
import com.eterna.backend.core.auth.application.commands.login.LoginOutputCommand;
import com.eterna.backend.core.shared.application.UseCase;

public interface LoginUseCase extends UseCase<LoginInputCommand, LoginOutputCommand> {
}
