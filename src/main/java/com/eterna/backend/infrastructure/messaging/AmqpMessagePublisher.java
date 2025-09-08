package com.eterna.backend.infrastructure.messaging;

import com.eterna.backend.core.shared.application.messaging.MessagePublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AmqpMessagePublisher implements MessagePublisher {
    @Value("${app.rabbit.exchange}")
    private String EXCHANGE_NAME;

    private final RabbitTemplate rabbitTemplate;

    public AmqpMessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(String topic, String message) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, topic, message);
    }
}
