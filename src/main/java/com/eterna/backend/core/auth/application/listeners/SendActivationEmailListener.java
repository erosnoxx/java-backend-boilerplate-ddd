package com.eterna.backend.core.auth.application.listeners;

import com.eterna.backend.core.auth.domain.events.UserSignedUpEvent;
import com.eterna.backend.core.shared.application.events.DomainEventListener;
import com.eterna.backend.core.shared.application.messaging.MessagePublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendActivationEmailListener implements DomainEventListener<UserSignedUpEvent> {
    @Autowired
    private MessagePublisher publisher;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(UserSignedUpEvent event) {
        try {
            var message = objectMapper.writeValueAsString(event);
            publisher.publish(event.getEventType(), message);

        } catch (Exception e) {
            throw new RuntimeException(
                    "failed to publish user registered message for user: " +
                            event.userId(), e);
        }
    }

    @Override
    public Class<UserSignedUpEvent> getEventType() {
        return UserSignedUpEvent.class;
    }
}
