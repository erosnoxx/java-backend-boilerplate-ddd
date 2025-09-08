package com.eterna.backend.core.shared.application.events;

import com.eterna.backend.core.shared.domain.events.DomainEvent;

public interface DomainEventListener<T extends DomainEvent> {
    void handle(T event);
    Class<T> getEventType();
}
