package com.example.store.kafka;

import com.example.store.kafka.dto.OrderCancelKafkaMessage;
import com.example.store.kafka.dto.OrderKafkaMessage;
import com.example.store.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.example.store.common.KafkaTopics.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumer {
    private final OrderService orderService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = TOPIC_ORDER_CREATE, groupId = "order-group")
    public void listenCreate(String message){
        try{
            OrderKafkaMessage orderKafkaMessage = objectMapper.readValue(message, OrderKafkaMessage.class);
            Long orderId = orderService.handleOrderCreate(orderKafkaMessage);
            log.info(" 주문 저장 완료: " + orderId);
        } catch (Exception e){
            log.error(" 주문 저장 실패", e);
        }
    }

    @KafkaListener(topics = TOPIC_ORDER_CANCEL, groupId = "order-group")
    public void listenCancel(String message) {
        try {
            OrderCancelKafkaMessage cancelMessage = objectMapper.readValue(message, OrderCancelKafkaMessage.class);
            orderService.handleOrderCancel(cancelMessage.getOrderId());
            log.info(" 주문 취소 완료: " + cancelMessage.getOrderId());
        } catch (Exception e) {
            log.error(" 주문 취소 실패", e);
        }
    }
}
