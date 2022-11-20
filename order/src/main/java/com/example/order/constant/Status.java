package com.example.order.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    ORDER_CREATED("주문시작"),
    INVENTORY_RESERVED("상품준비"),
    PAYMENT_COMPLETE("결제완료"),
    ORDER_COMPLETE("주문완료"),
    ORDER_CANCEL("주문취소");

    private final String description;
}
