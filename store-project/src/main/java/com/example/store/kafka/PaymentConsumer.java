package com.example.store.kafka;

import com.example.store.kafka.dto.PaymentRequestEvent;
import com.example.store.kafka.dto.PaymentResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.example.store.common.KafkaTopics.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentConsumer {
    private final KafkaTemplate<String, PaymentResultEvent> kafkaTemplate;
    private final Random random = new Random();

    @KafkaListener(topics = TOPIC_PAYMENT_REQUEST, groupId = "payment-group")
    public void handlePayment(PaymentRequestEvent event) {
        boolean isSuccess = random.nextBoolean(); // 임의로 성공/실패

        PaymentResultEvent result = new PaymentResultEvent();
        result.setOrderId(event.getOrderId());
        result.setUserId(event.getUserId());
        result.setAmount(event.getAmount());
        result.setSuccess(isSuccess);

        kafkaTemplate.send(TOPIC_PAYMENT_RESULT, result);

        log.info("결제 결과 발행: orderId={}, userId={}, amount={}, success={}",
                result.getOrderId(), result.getUserId(), result.getAmount(), result.isSuccess());
    }
}
