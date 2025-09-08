package com.eterna.backend.infrastructure.config.misc;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbit.exchange}")
    private String exchangeName;

    @Value("${app.rabbit.queues.activation}")
    private String activationQueue;

    @Value("${app.rabbit.routing.activation}")
    private String activationRoutingKey;

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue activationQueue() {
        return QueueBuilder.durable(activationQueue).build();
    }

    @Bean
    public Binding bindActivationQueue(Queue activationQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(activationQueue)
                .to(appExchange)
                .with(activationRoutingKey);
    }
}
