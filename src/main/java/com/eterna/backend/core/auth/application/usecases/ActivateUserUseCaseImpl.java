package com.eterna.backend.core.auth.application.usecases;

import com.eterna.backend.core.auth.application.commands.input.ActivateUserInputCommand;
import com.eterna.backend.core.auth.application.contracts.repositories.UserAuthRepository;
import com.eterna.backend.core.auth.application.contracts.usecases.ActivateUserUseCase;
import com.eterna.backend.core.auth.application.criteria.UserAuthCriteria;
import com.eterna.backend.core.auth.domain.exception.AuthenticationFailedException;
import com.eterna.backend.core.auth.domain.objects.OtpCode;
import com.eterna.backend.core.shared.application.services.CacheService;
import com.eterna.backend.core.shared.domain.enums.EntityStatus;
import com.eterna.backend.core.shared.domain.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActivateUserUseCaseImpl implements ActivateUserUseCase {
    @Autowired
    private UserAuthRepository repository;
    @Autowired
    private CacheService cacheService;

    @Override
    public Void execute(ActivateUserInputCommand command) {
        var user = repository.findById(command.userId())
                .orElseThrow(() -> new NotFoundException("user not found"));

        var otpCode = OtpCode.of(command.otpCode());

        boolean isValid = cacheService.get(otpCode.getKey(command.userId()), String.class)
                .map(storedOtp -> storedOtp.equals(otpCode.getValue()))
                .orElse(false);

        if (!isValid) throw new AuthenticationFailedException("invalid otp code");

        if (user.getStatus() != EntityStatus.ACTIVE) {
            user.activate();
            repository.save(user);
        }

        cacheService.delete(otpCode.getKey(user.getId()));

        return null;
    }
}
