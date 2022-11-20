package com.example.order.dto;

import com.example.order.constant.Status;
import com.example.order.domain.OrderEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderDto {

    @Data
    public static class OrderRegisterRequest{
        Order order;
        Inventory inventory;
    }

    @Data
    public static class Order {
        @JsonProperty("user_id")
        String userId;
        String name;
        String addr;
        String tel;
        String email;

        public OrderEntity toEntity() {
            OrderEntity order = OrderEntity.builder()
                    .userId(userId)
                    .name(name)
                    .addr(addr)
                    .tel(tel)
                    .email(email)
                    .build();
            return order;
        }
    }

    @Data
    public static class Inventory {
        Long id;
        Integer quantity;
    }

    @Data
    public static class OrderRegisterResponse{
        private String userId;
        private String transactionId;
        private Status status;

        public static OrderRegisterResponse formEntity(OrderEntity entity) {
            OrderRegisterResponse response = new OrderRegisterResponse();
            response.userId = entity.getUserId();
            response.transactionId = entity.getTransactionId();
            response.status = entity.getStatus();

            return response;
        }
    }
}
