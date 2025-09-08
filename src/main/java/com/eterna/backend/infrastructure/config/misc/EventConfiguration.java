package com.eterna.backend.infrastructure.config.misc;

import com.eterna.backend.core.shared.application.events.DomainEventListener;
import com.eterna.backend.core.shared.application.events.EventPublisher;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration @Slf4j
public class EventConfiguration {
    private final EventPublisher eventPublisher;
    private final List<DomainEventListener<?>> listeners;

    // 1. INJEÇÃO MÁGICA: Spring coleta TODOS os beans que implementam DomainEventListener
    public EventConfiguration(
            EventPublisher eventPublisher,
            @Autowired(required = false) List<DomainEventListener<?>> listeners) {
        //                  ↑
        // required = false: Se não houver nenhum listener, não quebra
        // List<DomainEventListener<?>>: Spring automaticamente coleta todos!

        this.eventPublisher = eventPublisher;
        this.listeners = listeners != null ? listeners : List.of();
    }

    // 2. REGISTRO AUTOMÁTICO: Após Spring criar todos os beans
    @PostConstruct  // ← Executa depois que TODOS os beans estão prontos
    public void registerListeners() {
        log.info("Registering " + listeners.size() + " event listeners:");

        for (var listener : listeners) {
            log.info("  - " + listener.getClass().getSimpleName() +
                    " for " + listener.getEventType().getSimpleName());
            eventPublisher.register(listener);
        }

        log.info("Event system ready!");
    }
}
