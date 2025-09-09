package com.eterna.backend.core.auth.domain.services;

public interface UserUniquenessChecker {
    boolean isUnique(String email);
    boolean adminExists();
}
