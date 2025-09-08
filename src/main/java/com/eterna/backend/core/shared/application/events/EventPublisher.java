package com.eterna.backend.core.shared.application.events;

import com.eterna.backend.core.shared.domain.events.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public class EventPublisher {

    private final List<DomainEventListener<? extends DomainEvent>> listeners = new ArrayList<>();

    public void register(DomainEventListener<? extends DomainEvent> listener) {
        listeners.add(listener);
    }

    public void publish(DomainEvent event) {
        for (var listener : listeners) {
            if (!listener.getEventType().isAssignableFrom(event.getClass()))
                continue;

            @SuppressWarnings("unchecked")
            var typedListener = (DomainEventListener<DomainEvent>) listener;
            typedListener.handle(event);
        }
    }
}
