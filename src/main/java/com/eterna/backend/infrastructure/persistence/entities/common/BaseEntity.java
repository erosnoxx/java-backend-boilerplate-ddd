package com.eterna.backend.infrastructure.persistence.entities.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass @Getter
public abstract class BaseEntity<T> {
    @Id
    @Setter @GeneratedValue(strategy = GenerationType.AUTO)
    private T id;

    @Column(name = "created_at", updatable = false, nullable = false) @Setter
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
