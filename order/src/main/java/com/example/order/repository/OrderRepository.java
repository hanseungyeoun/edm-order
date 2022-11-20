package com.example.order.repository;

import com.example.order.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByUserIdAndTransactionId(String userId, String transactionId);
}
