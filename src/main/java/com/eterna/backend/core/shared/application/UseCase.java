package com.eterna.backend.core.shared.application;

public interface UseCase<C, R> {
    R execute(C command);
}
