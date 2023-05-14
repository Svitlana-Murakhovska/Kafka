package com.example.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {

    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final String orderTopic;

    public ClientService(KafkaTemplate<String, Order> kafkaTemplate, @Value("${palmetto.order-topic}") String orderTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderTopic = orderTopic;
    }

    public void placeOrder(Order order) {
        // Set the correlation ID for the order
        String correlationId = UUID.randomUUID().toString();
      //  order.setCorrelationId(correlationId);

        // Send the order to the Kafka topic
        kafkaTemplate.send(orderTopic, correlationId, order);
    }

}
