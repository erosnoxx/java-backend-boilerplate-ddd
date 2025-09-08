package com.eterna.backend.core.auth.domain.events;

import com.eterna.backend.core.shared.domain.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserRegisteredEvent(
        UUID userId,
        LocalDateTime occurredOn)
        implements DomainEvent {

    public UserRegisteredEvent(UUID userId) {
        this(userId, LocalDateTime.now());
    }

    @Override
    public UUID getAggregateId() {
        return this.userId;
    }

    @Override
    public LocalDateTime getOccurredAt() {
        return this.occurredOn;
    }

    @Override
    public String getEventType() {
        return "user.registered";
    }
}
