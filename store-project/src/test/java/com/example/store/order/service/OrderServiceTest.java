package com.example.store.order.service;

import com.example.store.order.domain.Order;
import com.example.store.order.domain.OrderStatus;
import com.example.store.order.dto.OrderItemDto;
import com.example.store.order.dto.OrderRequestDto;
import com.example.store.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;


    @Test
    void createOrder() {
        // given
        OrderItemDto item1 = new OrderItemDto();
        item1.setItemName("Item A");
        item1.setQuantity(5);
        item1.setPrice(1000);

        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setUserId("testUser");
        requestDto.setTotalAmount(5000);
        requestDto.setItems(List.of(item1));

        // when
        Long orderId = orderService.createOrder(requestDto);

        // then
        Order savedOrder = orderRepository.findById(orderId).orElseThrow();
        assertThat(savedOrder.getUserId()).isEqualTo("testUser");
        assertThat(savedOrder.getTotalAmount()).isEqualTo(5000);
        assertThat(savedOrder.getStatus()).isEqualTo(OrderStatus.SE);
        assertThat(savedOrder.getItems()).hasSize(1);
        assertThat(savedOrder.getItems().get(0).getItemName()).isEqualTo("Item A");
    }

    @Test
    void cancelOrder() {
        // given
        OrderItemDto item1 = new OrderItemDto();
        item1.setItemName("item B");
        item1.setQuantity(1);
        item1.setPrice(5000);

        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setUserId("testUser");
        requestDto.setTotalAmount(5000);
        requestDto.setItems(List.of(item1));

        Long orderId = orderService.createOrder(requestDto);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order cancelledOrder = orderRepository.findById(orderId).orElseThrow();
        assertThat(cancelledOrder.getStatus()).isEqualTo(OrderStatus.CA);
    }
}