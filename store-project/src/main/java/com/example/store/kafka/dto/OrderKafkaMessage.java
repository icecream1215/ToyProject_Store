package com.example.store.kafka.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderKafkaMessage {
    private String userId;
    private Integer totalAmount;
    private List<OrderItemKafkaMessage> items;
}
