package com.eterna.backend.api.rest.schemas.response.user;

import com.eterna.backend.core.auth.domain.enums.Role;
import com.eterna.backend.core.shared.domain.enums.EntityStatus;
import com.eterna.backend.infrastructure.persistence.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        Role role,
        EntityStatus status,
        LocalDateTime lastLogin,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
    public static UserResponse of(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.getLastLogin(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }
}
