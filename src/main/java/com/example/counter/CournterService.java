package com.example.counter;

import org.springframework.stereotype.Service;

@Service
public class CournterService {

    private final KafkaTemplate<String, OrderStatus> kafkaTemplate;
    private final String notificationTopic;

    public CournterService(KafkaTemplate<String, OrderStatus> kafkaTemplate,
                           @Value("${palmetto.notification-topic}") String notificationTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.notificationTopic = notificationTopic;
    }

    @KafkaListener(topics = "${palmetto.notification-topic}", containerFactory = "kafkaListenerContainerFactory")
    public void receiveOrderStatus(@Payload OrderStatus orderStatus, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        // Process the order status
        // ...

        // Send a new order status to the Notification topic
        OrderStatus newOrderStatus = new
