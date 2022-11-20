package com.example.inventory.dto;

import com.example.inventory.constant.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderEvent {

    Order order;
    Inventory inventory;
    @JsonProperty("transaction_id")
    String transactionId;

    @JsonProperty("total_price")
    Status status;

    @Data
    public static class Order {
        @JsonProperty("user_id")
        String userId;
        String name;
        String addr;
        String tel;
        String email;
    }

    @Data
    public static class Inventory {
        Long id;
        Integer quantity;
        Integer totalPrice;
    }

    public void setTotalPrice(Integer price) {
        this.inventory.setTotalPrice(price);
    }
}
