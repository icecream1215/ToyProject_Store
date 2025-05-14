package com.example.store.payment.service;

import com.example.store.payment.domain.Payment;
import com.example.store.payment.domain.PaymentStatus;
import com.example.store.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public void savePaymentResult(Long orderId, String userId, int amount, boolean isSuccess) {
        Payment payment = Payment.builder()
                .orderId(orderId)
                .userId(userId)
                .amount(amount)
                .status(isSuccess ? PaymentStatus.SUCCESS : PaymentStatus.FAILED)
                .build();

        paymentRepository.save(payment);
    }
}
