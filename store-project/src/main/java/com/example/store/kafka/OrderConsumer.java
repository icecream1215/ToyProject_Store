package com.example.store.kafka;

import com.example.store.kafka.dto.OrderCancelKafkaMessage;
import com.example.store.kafka.dto.OrderKafkaMessage;
import com.example.store.kafka.dto.PaymentRequestEvent;
import com.example.store.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.example.store.common.KafkaTopics.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumer {
    private final OrderService orderService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = TOPIC_ORDER_CREATE, groupId = "order-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenCreate(OrderKafkaMessage orderKafkaMessage){
        try{
            //OrderKafkaMessage orderKafkaMessage = objectMapper.readValue(message, OrderKafkaMessage.class);
            Long orderId = orderService.handleOrderCreate(orderKafkaMessage);
            log.info(" 주문 저장 완료: " + orderId);

            PaymentRequestEvent paymentRequest = new PaymentRequestEvent();
            paymentRequest.setOrderId(orderId);
            paymentRequest.setAmount(orderKafkaMessage.getTotalAmount());
            paymentRequest.setUserId(orderKafkaMessage.getUserId());

            kafkaTemplate.send("payment-request", paymentRequest);
            log.info(" 결제 요청 발행 완료: {}", paymentRequest);
        } catch (Exception e){
            log.error(" 주문 저장 실패", e);
        }
    }

    @KafkaListener(topics = TOPIC_ORDER_CANCEL, groupId = "order-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenCancel(OrderCancelKafkaMessage cancelMessage) {
        try {
            //OrderCancelKafkaMessage cancelMessage = objectMapper.readValue(message, OrderCancelKafkaMessage.class);
            orderService.handleOrderCancel(cancelMessage.getOrderId());
            log.info(" 주문 취소 완료: " + cancelMessage.getOrderId());
        } catch (Exception e) {
            log.error(" 주문 취소 실패", e);
        }
    }
}
