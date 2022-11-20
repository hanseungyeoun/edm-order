package com.example.order.service;

import com.example.order.common.exception.EntityNotFoundException;
import com.example.order.domain.OrderEntity;
import com.example.order.dto.OrderEvent;
import com.example.order.dto.OrderResponse;
import com.example.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.order.dto.OrderDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public OrderRegisterResponse saveOrder(Order order) {
        OrderEntity orderEntity = order.toEntity();
        OrderEntity savedOrder = orderRepository.save(orderEntity);
        return OrderRegisterResponse.formEntity(savedOrder);
    }

    public OrderResponse getOrder(String userId, String transactionId) {
        OrderEntity orderEntity = orderRepository.findByUserIdAndTransactionId(userId, transactionId)
                .orElseThrow(() -> new EntityNotFoundException("OrderService.getOrder.OrderEntity"));
        return OrderResponse.formEntity(orderEntity);
    }

    @Transactional
    public void processPaymentComplete(String transactionId, String userId) {
        OrderEntity orderEntity = orderRepository.findByUserIdAndTransactionId(userId, transactionId)
                .orElseThrow(() -> new EntityNotFoundException("OrderService.getOrder.OrderEntity"));

        orderEntity.changeOrderComplete();
    }

    @Transactional
    public void processOrderCancel(String transactionId, String userId) {
        OrderEntity orderEntity = orderRepository.findByUserIdAndTransactionId(userId, transactionId)
                .orElseThrow(() -> new EntityNotFoundException("OrderService.getOrder.OrderEntity"));

        orderEntity.changeOrderCancel();
    }
}
