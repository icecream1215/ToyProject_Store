package com.example.store.kafka;

import com.example.store.kafka.dto.PaymentResultEvent;
import com.example.store.order.domain.Order;
import com.example.store.order.domain.OrderStatus;
import com.example.store.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.example.store.common.KafkaTopics.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderStatusUpdateConsumer {
    private final OrderRepository orderRepository;

    @KafkaListener(topics = TOPIC_PAYMENT_RESULT, groupId = "order-status-group")
    public void updateOrderStatus(PaymentResultEvent event) {
        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        if (event.isSuccess()) {
            order.setStatus(OrderStatus.PA);  // 결제 성공
        } else {
            order.setStatus(OrderStatus.FA);  // 결제 실패
        }

        orderRepository.save(order);
        log.info(" 주문 상태 변경 완료: " + order.getId() + " → " + order.getStatus().getDescription());
    }
}
