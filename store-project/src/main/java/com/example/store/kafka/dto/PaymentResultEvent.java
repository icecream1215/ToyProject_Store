package com.example.store.kafka.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResultEvent {
    private Long orderId;
    private boolean success;
}
