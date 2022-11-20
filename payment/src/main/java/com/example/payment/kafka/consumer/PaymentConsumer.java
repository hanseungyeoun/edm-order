package com.example.payment.kafka.consumer;

import com.example.payment.dto.OrderEvent;
import com.example.payment.kafka.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentService paymentService;

    @KafkaListener(id = "payment_consumer_group", topics = "payment", containerFactory = "kafkaJsonContainerFactory")
    public void listen(@Valid OrderEvent orderEvent) {
        paymentService.payment(orderEvent);
    }
}
