package com.example.store.order.service;

import com.example.store.kafka.dto.OrderItemKafkaMessage;
import com.example.store.kafka.dto.OrderKafkaMessage;
import com.example.store.order.domain.Order;
import com.example.store.order.domain.OrderItem;
import com.example.store.order.domain.OrderStatus;
import com.example.store.order.dto.OrderItemDto;
import com.example.store.order.dto.OrderRequestDto;
import com.example.store.order.repository.OrderRepository;
import com.example.store.product.domain.Product;
import com.example.store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long createOrder(OrderRequestDto requestDto) {
        List<Long> productIds = requestDto.getItems().stream()
                .map(OrderItemDto::getProductId)
                .collect(Collectors.toList());

        List<Product> products = productRepository.findAllById(productIds);
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        for (OrderItemDto item : requestDto.getItems()) {
            Product product = productMap.get(item.getProductId());

            if (product == null) {
                throw new IllegalArgumentException("상품 ID " + item.getProductId() + "는 존재하지 않습니다.");
            }

            if (product.getStockQuantity() < item.getQuantity()) {
                throw new IllegalStateException("상품 '" + product.getItemName() + "'의 재고가 부족합니다.");
            }
            product.decreaseStock(item.getQuantity());
        }
        productRepository.saveAll(products);

        Order order = new Order();
        order.setUserId(requestDto.getUserId());
        order.setStatus(OrderStatus.SE); // 생성
        order.setTotalAmount(requestDto.getTotalAmount());

        for (OrderItemDto itemDto : requestDto.getItems()) {
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
    public Long handleOrderCreate(OrderKafkaMessage requestDto){
        List<Long> productIds = requestDto.getItems().stream()
                .map(OrderItemKafkaMessage::getProductId)
                .collect(Collectors.toList());

        List<Product> products = productRepository.findAllById(productIds);
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        Order order = new Order();
        order.setUserId(requestDto.getUserId());
        order.setStatus(OrderStatus.SE);
        order.setTotalAmount(requestDto.getTotalAmount());

        for(OrderItemKafkaMessage itemDto : requestDto.getItems()){
            Product product = productMap.get(itemDto.getProductId());
            if (product == null) {
                throw new IllegalArgumentException("상품 ID " + itemDto.getProductId() + "는 존재하지 않습니다.");
            }

            if (product.getStockQuantity() < itemDto.getQuantity()) {
                throw new IllegalStateException("상품 '" + product.getItemName() + "'의 재고가 부족합니다.");
            }

            product.decreaseStock(itemDto.getQuantity());

            OrderItem item = new OrderItem();
            item.setItemName(itemDto.getItemName());
            item.setQuantity(itemDto.getQuantity());
            item.setPrice(itemDto.getPrice());
            item.setOrder(order);

            order.getItems().add(item);
        }
        productRepository.saveAll(products);
        Order savedOrder = orderRepository.save(order);

        return savedOrder.getId(); // 주문번호
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 주문번호로 주문 정보를 찾을 수 없습니다. id=" + orderId));
        order.setStatus(OrderStatus.CA); // 취소로 변경
    }

    @Transactional
    public void handleOrderCancel(Long orderId) {
        cancelOrder(orderId);
    }
}
