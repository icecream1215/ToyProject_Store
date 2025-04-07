package com.example.store.kafka;

import com.example.store.order.dto.OrderRequestDto;
import com.example.store.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.example.store.common.KafkaTopics.*;

@Service
@RequiredArgsConstructor
public class OrderConsumer {
    private final OrderService orderService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = TOPIC_ORDER_CREATE, groupId = "order-group")
    public void listenCreate(String message){
        try{
            OrderRequestDto orderRequestDto = objectMapper.readValue(message, OrderRequestDto.class);
            Long orderId = orderService.handleOrderCreate(orderRequestDto);

            System.out.println("🟢 주문 저장 완료: " + orderId);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = TOPIC_ORDER_CANCEL, groupId = "order-group")
    public void listenCancel(String message) {
        try {
            Long orderId = Long.valueOf(message);
            orderService.handleOrderCancel(orderId);
            System.out.println("🟠 주문 취소 완료: " + orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
