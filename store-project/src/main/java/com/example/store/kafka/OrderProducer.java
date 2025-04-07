package com.example.store.kafka;

import com.example.store.order.dto.OrderRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.example.store.common.KafkaTopics.*;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendOrderCreate(OrderRequestDto orderRequestDto) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(orderRequestDto);
            kafkaTemplate.send(TOPIC_ORDER_CREATE, jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendOrderCancel(Long orderId) {
        try {
            kafkaTemplate.send(TOPIC_ORDER_CANCEL, String.valueOf(orderId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
