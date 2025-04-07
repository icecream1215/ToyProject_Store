package com.example.store.order.service;

import com.example.store.order.domain.Order;
import com.example.store.order.domain.OrderItem;
import com.example.store.order.domain.OrderStatus;
import com.example.store.order.dto.OrderItemDto;
import com.example.store.order.dto.OrderRequestDto;
import com.example.store.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public Long handleOrderCreate(OrderRequestDto requestDto){
        Order order = new Order();
        order.setUserId(requestDto.getUserId());
        order.setStatus(OrderStatus.SE);
        order.setTotalAmount(requestDto.getTotalAmount());

        for(OrderItemDto itemDto : requestDto.getItems()){
            OrderItem item = new OrderItem();
            item.setItemName(itemDto.getItemName());
            item.setQuantity(itemDto.getQuantity());
            item.setPrice(itemDto.getPrice());
            item.setOrder(order);

            order.getItems().add(item);
        }

        Order savedOrder = orderRepository.save(order);

        return savedOrder.getId(); // 주문번호
    }

    @Transactional
    public void handleOrderCancel(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 주문번호로 주문 정보를 찾을 수 없습니다."));

        order.setStatus(OrderStatus.CA); // 취소로 변경
    }
}
