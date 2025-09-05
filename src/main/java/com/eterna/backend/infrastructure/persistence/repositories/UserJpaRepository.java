package com.eterna.backend.infrastructure.persistence.repositories;

import com.eterna.backend.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    UserDetails findUserByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
}
