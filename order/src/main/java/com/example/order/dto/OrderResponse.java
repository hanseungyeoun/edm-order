package com.example.order.dto;

import com.example.order.constant.Status;
import com.example.order.domain.OrderEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderResponse {
    @JsonProperty("user_id")
    String userId;
    @JsonProperty("transaction_id")
    String transactionId;
    String name;
    String addr;
    String tel;
    String email;
    Status status;

    public static OrderResponse formEntity(OrderEntity entity) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setUserId(entity.getUserId());
        orderResponse.setTransactionId(entity.getTransactionId());
        orderResponse.setName(entity.getName());
        orderResponse.setAddr(entity.getAddr());
        orderResponse.setTel(entity.getTel());
        orderResponse.setEmail(entity.getEmail());
        orderResponse.setStatus(entity.getStatus());
        return orderResponse;
    }
}
