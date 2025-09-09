package com.eterna.backend.core.auth.application.usecases;

import com.eterna.backend.core.auth.application.commands.input.RegisterUserInputCommand;
import com.eterna.backend.core.auth.application.contracts.repositories.UserAuthRepository;
import com.eterna.backend.core.auth.application.contracts.usecases.RegisterUserUseCase;
import com.eterna.backend.core.auth.domain.entities.UserAuth;
import com.eterna.backend.core.auth.domain.objects.UserName;
import com.eterna.backend.core.auth.domain.services.UserUniquenessChecker;
import com.eterna.backend.core.shared.application.events.EventPublisher;
import com.eterna.backend.core.shared.domain.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {
    @Autowired
    private UserAuthRepository repository;
    @Autowired
    private EventPublisher publisher;
    @Autowired
    private UserUniquenessChecker checker;

    @Override
    public UUID execute(RegisterUserInputCommand command) {
        var user = UserAuth.signUpNew(
                UserName.of(command.name()),
                Email.of(command.email()),
                command.role(),
                checker);
        var events = user.pullEvents();
        var savedUser = repository.save(user);
        events.forEach(publisher::publish);

        return savedUser.getId();
    }
}
