package com.eterna.backend.core.shared.domain.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public abstract class Domain<T> {
    private T id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
