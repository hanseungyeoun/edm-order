package com.example.inventory.domain;

import com.example.inventory.common.exception.InvalidParamException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inventory")
@EntityListeners(AuditingEntityListener.class)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;
    Integer price;
    Integer quantity;

    public Inventory(String name, Integer price, Integer quantity) {
        if(!StringUtils.hasText(name)) throw new InvalidParamException("inventory.name");
        if(price == null) throw new InvalidParamException("inventory.price");
        if(quantity == null) throw new InvalidParamException("inventory.quantity");

        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void changeQuantity(Integer quantity) {
        this.quantity-=quantity;
    }

    public void cancel(Integer quantity) {
        this.quantity+=quantity;
    }
}
