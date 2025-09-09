package com.eterna.backend.infrastructure.messaging.consumers;

import com.eterna.backend.core.auth.application.contracts.usecases.SendActivationEmailUseCase;
import com.eterna.backend.core.auth.domain.events.UserSignedUpEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ActivationEmailConsumer {

    private final SendActivationEmailUseCase sendActivationEmailUseCase;
    private final ObjectMapper objectMapper;

    public ActivationEmailConsumer(SendActivationEmailUseCase sendActivationEmailUseCase,
                                   ObjectMapper objectMapper) {
        this.sendActivationEmailUseCase = sendActivationEmailUseCase;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${app.rabbit.queues.activation}")
    public void handleActivationEmail(String message) {
        try {
            var event = objectMapper.readValue(message, UserSignedUpEvent.class);
            sendActivationEmailUseCase.execute(event.userId());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar mensagem de ativação", e);
        }
    }
}
