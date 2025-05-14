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

    // 결제 성공
    public static final String TOPIC_PAYMENT_SUCCESS = "payment-success";
    //결제 실패
    public static final String TOPIC_PAYMENT_FAIL = "payment-fail";
}
