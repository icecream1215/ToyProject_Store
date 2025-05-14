package com.example.store.kafka;

import com.example.store.kafka.dto.PaymentResultEvent;
import com.example.store.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.example.store.common.KafkaTopics.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentResultConsumer {
    private final PaymentService paymentService;

    @KafkaListener(topics = TOPIC_PAYMENT_RESULT, groupId = "payment-group")
    public void handlePaymentResult(PaymentResultEvent event) {
        paymentService.savePaymentResult(
                event.getOrderId(),
                event.getUserId(),
                event.getAmount(),
                event.isSuccess()
        );

        log.info("결제 결과 수신 및 저장: orderId={}, success={}", event.getOrderId(), event.isSuccess());
    }
}
