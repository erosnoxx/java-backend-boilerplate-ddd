package com.eterna.backend.infrastructure.mappers;

import com.eterna.backend.core.auth.domain.entities.UserAuth;
import com.eterna.backend.core.auth.domain.exception.AuthenticationFailedException;
import com.eterna.backend.core.auth.domain.objects.Password;
import com.eterna.backend.core.auth.domain.objects.UserName;
import com.eterna.backend.core.shared.application.EntityMapper;
import com.eterna.backend.core.shared.domain.objects.Email;
import com.eterna.backend.infrastructure.persistence.entities.UserEntity;
import com.eterna.backend.infrastructure.persistence.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAuthMapper implements EntityMapper<UserAuth, UserEntity> {
    @Autowired
    private UserJpaRepository repository;

    @Override
    public UserAuth toDomain(UserEntity entity) {
        var domain = new UserAuth(entity.getId());
        domain.setName(entity.getName());
        domain.setEmail(Email.of(entity.getEmail()));
        domain.setPasswordHash(Password.fromEncrypted(entity.getPasswordHash()));
        domain.setRole(entity.getRole());
        domain.setStatus(entity.getStatus());
        domain.setLastLogin(entity.getLastLogin());

        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());

        return domain;
    }

    @Override
    public UserEntity toEntity(UserAuth domain) {
        var entity = repository.findById(domain.getId())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        entity.setStatus(domain.getStatus());
        entity.setLastLogin(domain.getLastLogin());

        return entity;
    }
}
