package com.example.store.common;

public class KafkaTopics {
    // 주문 생성
    public static final String TOPIC_ORDER_CREATE = "order-create";
    // 주문 취소
    public static final String TOPIC_ORDER_CANCEL = "order-cancel";

    // 결제 요청
    public static final String TOPIC_PAYMENT_REQUEST = "payment-request";
    // 결제 결과
    public static final String TOPIC_PAYMENT_RESULT = "payment-result";
}
