package com.example.order.controller;

import com.example.order.common.respose.CommonResponse;
import com.example.order.dto.OrderEvent;
import com.example.order.dto.OrderModelMapper;
import com.example.order.dto.OrderDto;
import com.example.order.dto.OrderResponse;
import com.example.order.kafka.Producer.OrderProducer;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderProducer orderProducer;

    @Value("${my.inventory-topic-name}")
    private String topicName;

    @GetMapping("/v1/order")
    CommonResponse showTransaction(String userId, String transactionId){
        OrderResponse order = orderService.getOrder(userId, transactionId);
        return CommonResponse.success(order);
    }

    @PostMapping("/v1/order")
    CommonResponse createOrder(@RequestBody OrderDto.OrderRegisterRequest request){
        OrderDto.OrderRegisterResponse response = orderService.saveOrder(request.getOrder());
        OrderEvent orderEvent = OrderModelMapper.INSTANCE.of(request, response.getTransactionId(), response.getStatus());
        System.out.println("orderEvent = " + orderEvent);
        orderProducer.async(topicName, orderEvent.getTransactionId(), orderEvent);
        return CommonResponse.success(response);
    }

}
