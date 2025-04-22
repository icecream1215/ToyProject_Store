package com.example.store.order.controller;

import com.example.store.kafka.OrderProducer;
import com.example.store.kafka.dto.OrderCancelKafkaMessage;
import com.example.store.kafka.dto.OrderKafkaMessage;
import com.example.store.order.dto.OrderRequestDto;
import com.example.store.order.dto.OrderCreateResponseDto;
import com.example.store.order.dto.OrderResponseDto;
import com.example.store.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderProducer orderProducer;

    // api 직접 조회
    @PostMapping("/direct")
    public ResponseEntity<OrderCreateResponseDto> createOrderDirect(@RequestBody OrderRequestDto orderRequestDto) {
        Long orderId = orderService.createOrder(orderRequestDto);
        return new ResponseEntity<>(new OrderCreateResponseDto(orderId), HttpStatus.CREATED);
    }

    @PostMapping("/direct/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrderDirect(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //
    @PostMapping
    public ResponseEntity<OrderCreateResponseDto> createOrder(@RequestBody OrderKafkaMessage orderKafkaMessage) {
        orderProducer.sendOrderCreate(orderKafkaMessage);
        return new ResponseEntity<>(new OrderCreateResponseDto(null), HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        OrderCancelKafkaMessage cancelMessage = new OrderCancelKafkaMessage();
        cancelMessage.setOrderId(orderId);
        orderProducer.sendOrderCancel(cancelMessage);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getMyOrders(String userId) { // 로그인기능 추가 후 Principal principal 또는 @AuthenticationPrincipal UserDetails user로 변경
        List<OrderResponseDto> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }
}
