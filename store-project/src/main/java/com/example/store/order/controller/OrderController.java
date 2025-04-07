package com.example.store.order.controller;

import com.example.store.kafka.OrderProducer;
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
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        //Long orderId = orderService.createOrder(orderRequestDto);
        //return new ResponseEntity<>(new OrderResponseDto(orderId), HttpStatus.CREATED);
        orderProducer.sendOrderCreate(orderRequestDto);
        return new ResponseEntity<>(new OrderResponseDto(null), HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderProducer.sendOrderCancel(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
