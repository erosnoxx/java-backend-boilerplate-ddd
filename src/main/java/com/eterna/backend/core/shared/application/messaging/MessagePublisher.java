package com.eterna.backend.core.shared.application.messaging;

public interface MessagePublisher {
    void publish(String topic, String message);
}

