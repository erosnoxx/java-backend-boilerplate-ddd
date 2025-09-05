package com.eterna.backend.core.auth.application.usecases;

import com.eterna.backend.core.auth.application.commands.login.LoginInputCommand;
import com.eterna.backend.core.auth.application.commands.login.LoginOutputCommand;
import com.eterna.backend.core.auth.application.contracts.repositories.UserAuthRepository;
import com.eterna.backend.core.auth.application.contracts.services.AuthenticatorService;
import com.eterna.backend.core.auth.application.contracts.usecases.LoginUseCase;
import com.eterna.backend.core.auth.domain.entities.UserAuth;
import com.eterna.backend.core.auth.domain.exception.UserNotActiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eterna.backend.core.auth.domain.exception.AuthenticationFailedException;


@Service
public class LoginUseCaseImpl implements LoginUseCase {
    @Autowired
    private AuthenticatorService authenticatorService;
    @Autowired
    private UserAuthRepository repository;

    @Override
    public LoginOutputCommand execute(LoginInputCommand command) {
        var user = repository.findByEmail(command.email())
                .orElseThrow(() ->
                        new AuthenticationFailedException("Invalid email or password"));

        validate(user, command);
        var tokenPair = authenticatorService.getToken(command.email(), command.password());
        updateLastLogin(user);

        return LoginOutputCommand.of(tokenPair);
    }

    private void validate(UserAuth user, LoginInputCommand command) {
        if (!user.getPasswordHash().matches(command.password()))
            throw new AuthenticationFailedException("Invalid email or password");

        if (!user.canAccess())
            throw new UserNotActiveException("user cannot login: (inactive or suspended)");
    }

    private void updateLastLogin(UserAuth user) {
        user.registerLogin();
        repository.save(user);
    }
}
