package com.example.order.kafka.Consumer;

import com.example.order.common.exception.IllegalStatusException;
import com.example.order.constant.Status;
import com.example.order.dto.OrderEvent;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

import static com.example.order.constant.Status.ORDER_CANCEL;
import static com.example.order.constant.Status.PAYMENT_COMPLETE;

@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderService orderService;

    @KafkaListener(id = "order_consumer_group", topics = "order", containerFactory = "kafkaJsonContainerFactory")
    public void listen(@Valid OrderEvent orderEvent) {
        System.out.println("orderEvent = " + orderEvent);

        String transactionId = orderEvent.getTransactionId();
        String userId = orderEvent.getOrder().getUserId();
        Status status = orderEvent.getStatus();

        if(status == PAYMENT_COMPLETE) {
            orderService.processPaymentComplete(transactionId, userId);
        } else if(status == ORDER_CANCEL) {
            orderService.processOrderCancel(transactionId, userId);
        }
    }
}
