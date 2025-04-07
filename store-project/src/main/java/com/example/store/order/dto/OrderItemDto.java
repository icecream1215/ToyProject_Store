package com.example.store.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDto {
    private String itemName;
    private Integer quantity;
    private Integer price;
}
