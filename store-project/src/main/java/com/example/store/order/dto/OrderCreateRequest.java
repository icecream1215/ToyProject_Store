package com.example.store.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    private String orderId;      // 주문 ID
    private String itemName;     // 상품명
    private int quantity;        // 수량
    private int price;           // 가격
}