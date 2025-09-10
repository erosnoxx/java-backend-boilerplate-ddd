package com.eterna.backend.core.auth.application.usecases;

import com.eterna.backend.core.auth.application.commands.input.ChangePasswordInputCommand;
import com.eterna.backend.core.auth.application.contracts.repositories.UserAuthRepository;
import com.eterna.backend.core.auth.application.contracts.usecases.ChangePasswordUseCase;
import com.eterna.backend.core.shared.domain.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChangePasswordUseCaseImpl implements ChangePasswordUseCase {
    @Autowired
    private UserAuthRepository repository;

    @Override
    public Void execute(ChangePasswordInputCommand command) {
        var user = repository.findById(command.id())
                .orElseThrow(() -> new NotFoundException("user not found"));

        user.changePassword(command.oldPassword(), command.newPassword());
        repository.save(user);

        return null;
    }
}
