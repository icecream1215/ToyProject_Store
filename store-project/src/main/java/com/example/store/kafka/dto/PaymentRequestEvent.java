package com.example.store.kafka.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestEvent {
    private Long orderId;
    private Integer amount;
    private String userId;
}
