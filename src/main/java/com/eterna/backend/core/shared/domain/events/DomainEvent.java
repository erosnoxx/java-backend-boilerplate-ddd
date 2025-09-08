package com.eterna.backend.core.shared.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DomainEvent {
    UUID getAggregateId();
    LocalDateTime getOccurredAt();
    String getEventType();
}
