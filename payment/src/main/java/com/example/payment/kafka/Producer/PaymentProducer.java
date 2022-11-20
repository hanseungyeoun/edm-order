package com.example.payment.kafka.Producer;

import com.example.payment.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaJsonTemplate;

    public void async(String topic, String key, OrderEvent orderEvent) {
        kafkaJsonTemplate.send(topic, key, orderEvent);
    }
}
