package com.eterna.backend.core.auth.domain.checkers;

public interface UserUniquenessChecker {
    boolean isUnique(String email);
    boolean adminExists();
}
