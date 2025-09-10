package com.eterna.backend.infrastructure.repositories.user;

import com.eterna.backend.core.auth.application.contracts.repositories.UserAuthRepository;
import com.eterna.backend.core.auth.application.criteria.UserAuthCriteria;
import com.eterna.backend.core.auth.domain.entities.UserAuth;
import com.eterna.backend.core.auth.domain.enums.Role;
import com.eterna.backend.core.shared.application.Criteria;
import com.eterna.backend.infrastructure.mappers.UserAuthMapper;
import com.eterna.backend.infrastructure.persistence.entities.UserEntity;
import com.eterna.backend.infrastructure.persistence.repositories.UserJpaRepository;
import com.eterna.backend.infrastructure.repositories.common.RepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserAuthRepositoryImpl
        extends RepositoryImpl<UserAuth, UUID, UserEntity, UserJpaRepository>
        implements UserAuthRepository {

    public UserAuthRepositoryImpl(
            UserJpaRepository repository,
            EntityManager em,
            UserAuthMapper mapper) {
        super(repository, em, mapper, UserEntity.class);
    }

    @Override
    public Optional<UserAuth> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByRole(Role role) {
        return repository.existsByRole(role);
    }
}
