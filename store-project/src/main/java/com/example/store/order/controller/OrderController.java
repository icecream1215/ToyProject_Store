package com.example.store.order.controller;

import com.example.store.kafka.OrderProducer;
import com.example.store.kafka.dto.OrderCancelKafkaMessage;
import com.example.store.kafka.dto.OrderKafkaMessage;
import com.example.store.order.dto.OrderRequestDto;
import com.example.store.order.dto.OrderResponseDto;
import com.example.store.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderProducer orderProducer;

    @PostMapping("/direct")
    public ResponseEntity<OrderResponseDto> createOrderDirect(@RequestBody OrderRequestDto orderRequestDto) {
        Long orderId = orderService.createOrder(orderRequestDto);
        return new ResponseEntity<>(new OrderResponseDto(orderId), HttpStatus.CREATED);
    }

    @PostMapping("/direct/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrderDirect(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderKafkaMessage orderKafkaMessage) {
        orderProducer.sendOrderCreate(orderKafkaMessage);
        return new ResponseEntity<>(new OrderResponseDto(null), HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        OrderCancelKafkaMessage cancelMessage = new OrderCancelKafkaMessage();
        cancelMessage.setOrderId(orderId);
        orderProducer.sendOrderCancel(cancelMessage);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
