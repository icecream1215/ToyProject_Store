package com.example.store.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {
    private String userId;  // 사용자 ID
    private Integer totalAmount;    //총 결제 금액
    private List<OrderItemDto> items; // 주문할 상품 목록
}
