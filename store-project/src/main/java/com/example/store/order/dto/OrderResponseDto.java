package com.example.store.order.dto;

import com.example.store.order.domain.Order;
import com.example.store.order.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private String productName;
    private int totalPrice;
    private LocalDateTime orderDate;
    private String status;
    private String statusDescription;
    private String failReason;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        // 첫번째 상품이름
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            OrderItem firstItem = order.getItems().get(0);
            if (order.getItems().size() > 1) {
                this.productName = firstItem.getItemName() + " 외 " + (order.getItems().size() - 1) + "건";
            } else {
                this.productName = firstItem.getItemName();
            }
        } else {
            this.productName = "(상품 없음)";
        }
        this.totalPrice = order.getTotalAmount();
        this.orderDate = order.getCreatedDt();
        this.status = order.getStatus().name();
        this.statusDescription = order.getStatus().getDescription();
        this.failReason = null; // 없으면 기본 null
    }
}
