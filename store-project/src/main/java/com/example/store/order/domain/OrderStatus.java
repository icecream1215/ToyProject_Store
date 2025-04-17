package com.example.store.order.domain;

public enum OrderStatus {
    SE("SUBMITTED"),    // 주문 접수
    CA("CANCELLED"),    // 주문 취소
    PA("PAID"),          // 결제 완료
    FA("FAILED");        // 결제 실패

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
