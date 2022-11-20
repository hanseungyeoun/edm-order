package com.example.order.kafka.Producer;

import com.example.order.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaJsonTemplate;

    public void async(String topic, String key, OrderEvent orderEvent) {
        kafkaJsonTemplate.send(topic, key, orderEvent);
    }
}
