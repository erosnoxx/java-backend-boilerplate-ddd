package com.eterna.backend.core.auth.application.usecases;

import com.eterna.backend.core.auth.application.contracts.repositories.UserAuthRepository;
import com.eterna.backend.core.auth.application.contracts.usecases.TestUseCase;
import com.eterna.backend.core.auth.application.listeners.SendActivationEmailListener;
import com.eterna.backend.core.shared.application.events.EventPublisher;
import com.eterna.backend.core.shared.domain.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TestUseCaseImpl implements TestUseCase {
    @Autowired
    private UserAuthRepository repository;
    @Autowired
    private SendActivationEmailListener sendActivationEmailListener;

    @Override
    public Void execute(Void command) {
        var user = repository.findById(UUID.fromString("8fb37439-b139-4d1a-b111-3415392a84e5"))
                .orElseThrow(() -> new NotFoundException("user not found"));

        user.registered();
        var publisher = new EventPublisher();
        publisher.register(sendActivationEmailListener);
        user.pullEvents().forEach(publisher::publish);

        return null;
    }
}
