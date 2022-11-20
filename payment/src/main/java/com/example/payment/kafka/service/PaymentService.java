package com.example.payment.kafka.service;

import com.example.payment.dto.OrderEvent;
import com.example.payment.kafka.Producer.PaymentProducer;
import com.example.payment.kafka.consumer.PaymentConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.example.payment.constant.Status.ORDER_CANCEL;
import static com.example.payment.constant.Status.PAYMENT_COMPLETE;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${my.outbound-success-event-channel}")
    private String outboundSuccessEventChannel;

    @Value("${my.outbound-failure-event-channel}")
    private String outboundFailureEventChannel;

    @Value("${my.is_pg_api_call_succeeded}")
    private boolean isPgApiCallSucceeded;

    private final PaymentProducer paymentProducer;

    public void payment(OrderEvent orderEvent) {
        boolean result = processPayment(orderEvent);
        if(result) {
            orderEvent.setStatus(PAYMENT_COMPLETE);
            paymentProducer.async(outboundSuccessEventChannel, orderEvent.getTransactionId(), orderEvent);

        } else {
            orderEvent.setStatus(ORDER_CANCEL);
            paymentProducer.async(outboundFailureEventChannel, orderEvent.getTransactionId(), orderEvent);
        }
    }

    private boolean processPayment(OrderEvent orderEvent){
        return isPgApiCallSucceeded;
    }
}
