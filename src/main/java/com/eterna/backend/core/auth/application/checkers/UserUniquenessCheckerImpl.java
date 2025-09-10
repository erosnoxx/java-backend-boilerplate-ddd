package com.eterna.backend.core.auth.application.checkers;

import com.eterna.backend.core.auth.application.contracts.repositories.UserAuthRepository;
import com.eterna.backend.core.auth.domain.enums.Role;
import com.eterna.backend.core.auth.domain.checkers.UserUniquenessChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUniquenessCheckerImpl implements UserUniquenessChecker {
    @Autowired
    private UserAuthRepository repository;

    @Override
    public boolean isUnique(String email) {
        return !repository.existsByEmail(email);
    }

    @Override
    public boolean adminExists() {
        return repository.existsByRole(Role.ADMIN);
    }
}
