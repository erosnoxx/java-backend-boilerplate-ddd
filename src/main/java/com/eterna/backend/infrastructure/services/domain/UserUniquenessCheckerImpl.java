package com.eterna.backend.infrastructure.services.domain;

import com.eterna.backend.core.auth.domain.enums.Role;
import com.eterna.backend.core.auth.domain.services.UserUniquenessChecker;
import com.eterna.backend.infrastructure.persistence.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUniquenessCheckerImpl implements UserUniquenessChecker {
    @Autowired
    private UserJpaRepository repository;

    @Override
    public boolean isUnique(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean adminExists() {
        return repository.existsByRole(Role.ADMIN);
    }
}
