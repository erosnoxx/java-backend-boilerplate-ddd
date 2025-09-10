package com.eterna.backend.core.shared.application.services;

import java.time.Duration;
import java.util.Optional;

public interface CacheService {

    <T> void put(String key, T value, Duration ttl);

    <T> Optional<T> get(String key, Class<T> type);

    void delete(String key);

    boolean exists(String key);
}

