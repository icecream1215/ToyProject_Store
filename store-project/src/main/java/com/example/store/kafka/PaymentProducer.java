package com.example.store.kafka;

import com.example.store.kafka.dto.PaymentRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.example.store.common.KafkaTopics.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentRequest(PaymentRequestEvent event) {
        kafkaTemplate.send(TOPIC_PAYMENT_REQUEST, event);
        log.info("결제 요청 발행: orderId={}, userId={}, amount={}", event.getOrderId(), event.getUserId(), event.getAmount());
    }
}
