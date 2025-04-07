package com.example.store.kafka.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemKafkaMessage {
    private String itemName;
    private Integer quantity;
    private Integer price;
}
