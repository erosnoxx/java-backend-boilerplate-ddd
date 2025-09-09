package com.eterna.backend.core.auth.application.usecases;

import com.eterna.backend.core.auth.application.contracts.repositories.UserAuthRepository;
import com.eterna.backend.core.auth.application.contracts.usecases.ActivateUserUseCase;
import com.eterna.backend.core.shared.domain.enums.EntityStatus;
import com.eterna.backend.core.shared.domain.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActivateUserUseCaseImpl implements ActivateUserUseCase {
    @Autowired
    private UserAuthRepository repository;

    @Override
    public Void execute(UUID command) {
        var user = repository.findById(command)
                .orElseThrow(() -> new NotFoundException("user not found"));

        if (user.getStatus() != EntityStatus.ACTIVE) {
            user.activate();
            repository.save(user);
        }

        return null;
    }
}
