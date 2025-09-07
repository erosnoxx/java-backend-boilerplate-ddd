package com.eterna.backend.infrastructure.config.security;

import com.eterna.backend.api.rest.schemas.response.common.IdResponse;
import com.eterna.backend.core.auth.domain.exception.AuthenticationFailedException;
import com.eterna.backend.infrastructure.persistence.entities.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityUtils {

    public static IdResponse<UUID> getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(authentication instanceof UserEntity user)) {
            throw new AuthenticationFailedException("user not authenticated");
        }

        return new IdResponse<>(user.getId());
    }
}
