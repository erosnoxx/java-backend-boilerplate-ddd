package com.eterna.backend.core.shared.application;

public interface EntityMapper<D, E> {
    D toDomain(E entity);
    E toEntity(D domain);
}
