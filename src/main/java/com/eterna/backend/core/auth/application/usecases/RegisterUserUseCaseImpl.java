package com.eterna.backend.core.auth.application.usecases;

import com.eterna.backend.core.auth.application.commands.input.RegisterUserInputCommand;
import com.eterna.backend.core.auth.application.contracts.repositories.UserAuthRepository;
import com.eterna.backend.core.auth.application.contracts.usecases.RegisterUserUseCase;
import com.eterna.backend.core.auth.domain.entities.UserAuth;
import com.eterna.backend.core.auth.domain.objects.Password;
import com.eterna.backend.core.auth.domain.objects.UserName;
import com.eterna.backend.core.shared.application.events.EventPublisher;
import com.eterna.backend.core.shared.domain.exceptions.AlreadyExistsException;
import com.eterna.backend.core.shared.domain.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {
    @Autowired
    private UserAuthRepository repository;
    @Autowired
    private EventPublisher publisher;

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public UUID execute(RegisterUserInputCommand command) {

        validate(command.email());

        var user = new UserAuth(
                UserName.of(command.name()),
                Email.of(command.email()),
                Password.fromPlainText(generateRandomPassword(12)),
                command.role());

        var savedUser = repository.save(user);

        savedUser.registered();
        savedUser.pullEvents().forEach(publisher::publish);

        return savedUser.getId();
    }

    private void validate(String email) {
        repository.findByEmail(email)
                .ifPresent(u -> {
                    throw new AlreadyExistsException("user already registered");
                });
    }

    private static String generateRandomPassword(final int length) {
        if (length < 8) {
            throw new IllegalArgumentException("length must be at least 8");
        }

        final String lower = "abcdefghijklmnopqrstuvwxyz";
        final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String digits = "0123456789";

        // especiais permitidos — sem underscore
        final String specials = "!@#$%^&*()-+=<>?{}[]:;.,~`|/\\'\"";

        // garante ao menos um caractere de cada classe necessária
        List<Character> passwordChars = new ArrayList<>(length);

        // add at least one letter (could be upper or lower)
        String letterPool = lower + upper;
        passwordChars.add(randomCharFrom(letterPool));

        // add at least one digit
        passwordChars.add(randomCharFrom(digits));

        // add at least one special (\\W equiv, sem underscore)
        passwordChars.add(randomCharFrom(specials));

        // preencher o restante com mistura de pools
        String allPools = lower + upper + digits + specials;
        for (int i = passwordChars.size(); i < length; i++) {
            passwordChars.add(randomCharFrom(allPools));
        }

        // embaralha para não deixar padrão previsível
        Collections.shuffle(passwordChars, RANDOM);

        // monta string final
        StringBuilder sb = new StringBuilder(length);
        for (char c : passwordChars) {
            sb.append(c);
        }

        return sb.toString();
    }

    private static char randomCharFrom(final String pool) {
        int idx = RANDOM.nextInt(pool.length());
        return pool.charAt(idx);
    }
}
