package com.eterna.backend.core.auth.application.contracts.repositories;

import com.eterna.backend.core.auth.application.criteria.UserAuthCriteria;
import com.eterna.backend.core.auth.domain.entities.UserAuth;
import com.eterna.backend.core.auth.domain.enums.Role;
import com.eterna.backend.core.shared.application.Repository;

import java.util.Optional;
import java.util.UUID;

public interface UserAuthRepository extends Repository<UserAuth, UUID, UserAuthCriteria> {
    Optional<UserAuth> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByRole(Role role);
}
