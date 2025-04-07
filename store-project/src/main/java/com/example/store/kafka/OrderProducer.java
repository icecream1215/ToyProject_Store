package com.example.store.kafka;

import com.example.store.kafka.dto.OrderCancelKafkaMessage;
import com.example.store.kafka.dto.OrderKafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


import static com.example.store.common.KafkaTopics.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderCreate(OrderKafkaMessage message) {
        kafkaTemplate.send(TOPIC_ORDER_CREATE, message);
        log.info("주문 생성 메시지 발행 완료");
    }

    public void sendOrderCancel(OrderCancelKafkaMessage message) {
        kafkaTemplate.send(TOPIC_ORDER_CANCEL, message);
        log.info("주문 취소 메시지 발행 완료");
    }
}
