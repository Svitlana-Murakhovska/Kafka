package com.example.palmetto;

import org.springframework.stereotype.Service;

@Service
public class PalmettoService {

    private final KafkaTemplate<String, OrderStatus> kafkaTemplate;
    private final String orderTopic;
    private final String notificationTopic;

    public PalmettoService(KafkaTemplate<String, OrderStatus> kafkaTemplate,
                           @Value("${palmetto.order-topic}") String orderTopic,
                           @Value("${palmetto.notification-topic}") String notificationTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderTopic = orderTopic;
        this.notificationTopic = notificationTopic;
    }

    @KafkaListener(topics = "${palmetto.order-topic}", containerFactory = "kafkaListenerContainerFactory")
    public void processOrder(@Payload Order order, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        // Process the order
        // ...

        // Send a new order status to the Notification topic
        OrderStatus orderStatus = new OrderStatus(order.getCorrelationId(), OrderStatus.Status.PROCESSING);
        kafkaTemplate.send(notificationTopic, order.getCorrelationId(), orderStatus);
    }

    @KafkaListener(topics = "${palmetto.notification-topic}", containerFactory = "kafkaListenerContainerFactory")
    public void updateOrderStatus(@Payload OrderStatus orderStatus, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        // Update the order status in the database
        // ...
    }

}

