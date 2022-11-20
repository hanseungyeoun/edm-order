package com.example.order.domain;

import com.example.order.common.exception.InvalidParamException;
import com.example.order.constant.Status;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.order.constant.Status.*;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;
    private String userId;
    private String name;
    private String addr;
    private String tel;
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    private LocalDateTime updated;

    @Builder
    public OrderEntity(String userId, String name, String addr, String tel, String email) {
        if(!StringUtils.hasText(userId)) throw new InvalidParamException("order.user_id");
        if(!StringUtils.hasText(name)) throw new InvalidParamException("order.name");
        if(!StringUtils.hasText(addr)) throw new InvalidParamException("order.addr");
        if(!StringUtils.hasText(tel)) throw new InvalidParamException("order.tel");
        if(!StringUtils.hasText(email)) throw new InvalidParamException("order.email");


        this.userId = userId;
        this.name = name;
        this.addr = addr;
        this.tel = tel;
        this.email = email;
        this.transactionId = UUID.randomUUID().toString();
        this.status = ORDER_CREATED;
    }

    public void changeOrderComplete() {
        this.status = ORDER_COMPLETE;
    }

    public void changeOrderCancel() {
        this.status = ORDER_CANCEL;
    }
}
