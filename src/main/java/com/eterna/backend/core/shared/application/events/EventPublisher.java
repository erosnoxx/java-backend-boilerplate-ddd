package com.eterna.backend.core.shared.application.events;

import com.eterna.backend.core.shared.domain.events.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class EventPublisher {
    private static final Logger log = LoggerFactory.getLogger(EventPublisher.class);

    private final List<DomainEventListener<?extends DomainEvent>> listeners = new CopyOnWriteArrayList<>();

    public void register(DomainEventListener<? extends DomainEvent> listener) {
        if (listeners.contains(listener)) {
            log.warn("listener {} already registered - skipping",
                    listener.getClass().getSimpleName());
            return;
        }
        listeners.add(listener);
        log.debug("registered listener: {} for event: {}",
                listener.getClass().getSimpleName(),
                listener.getEventType().getSimpleName());
    }

    public void publish(DomainEvent event) {
        log.debug("publishing event: {} for aggregate: {}",
                event.getEventType(), event.getAggregateId());

        if (listeners.isEmpty()) {
            log.debug("no listeners registered for any events");
            return;
        }

        var processedCount = 0;

        for (var listener : listeners) {
            if (!listener.getEventType().isAssignableFrom(event.getClass())) {
                continue;
            }

            try {
                @SuppressWarnings("unchecked")
                var typedListener = (DomainEventListener<DomainEvent>) listener;

                log.trace("processing event {} with listener {}",
                        event.getEventType(), listener.getClass().getSimpleName());

                typedListener.handle(event);
                processedCount++;

            } catch (Exception e) {
                log.error("listener {} failed to process event {}: {}",
                        listener.getClass().getSimpleName(),
                        event.getEventType(),
                        e.getMessage(), e);
            }
        }

        log.debug("event {} processed by {} listeners",
                event.getEventType(), processedCount);
    }

    public void publishAll(List<DomainEvent> events) {
        if (events == null || events.isEmpty()) {
            return;
        }

        log.debug("publishing {} events", events.size());

        for (var event : events) {
            publish(event);
        }
    }

    public int getListenerCount() {
        return listeners.size();
    }

    public int getListenerCountFor(Class<? extends DomainEvent> eventType) {
        return (int) listeners.stream()
                .filter(
                        listener -> listener
                                .getEventType().isAssignableFrom(eventType))
                .count();
    }
}

