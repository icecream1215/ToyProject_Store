package com.example.store.order.domain;

public enum OrderStatus {
    SE("SUBMITTED"),    // 주문 접수
    CA("CANCELLED");    // 주문 취소

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
