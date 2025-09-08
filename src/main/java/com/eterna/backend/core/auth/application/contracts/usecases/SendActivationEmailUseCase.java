package com.eterna.backend.core.auth.application.contracts.usecases;

import com.eterna.backend.core.shared.application.UseCase;

import java.util.UUID;

public interface SendActivationEmailUseCase extends UseCase<UUID, Void> {
}
