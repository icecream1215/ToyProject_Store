package com.example.store.kafka.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResultEvent {
    private Long orderId;
    private String userId;
    private Integer amount;
    private boolean success;
}
