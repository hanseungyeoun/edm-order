package com.example.order.dto;

import com.example.order.constant.Status;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-19T20:59:45+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)"
)
public class OrderModelMapperImpl implements OrderModelMapper {

    @Override
    public OrderEvent.Inventory of(OrderDto.Inventory inventory) {
        if ( inventory == null ) {
            return null;
        }

        OrderEvent.Inventory inventory1 = new OrderEvent.Inventory();

        inventory1.setId( inventory.getId() );
        inventory1.setQuantity( inventory.getQuantity() );

        return inventory1;
    }

    @Override
    public OrderEvent.Order of(OrderDto.Order order) {
        if ( order == null ) {
            return null;
        }

        OrderEvent.Order order1 = new OrderEvent.Order();

        order1.setUserId( order.getUserId() );
        order1.setName( order.getName() );
        order1.setAddr( order.getAddr() );
        order1.setTel( order.getTel() );
        order1.setEmail( order.getEmail() );

        return order1;
    }

    @Override
    public OrderEvent of(OrderDto.OrderRegisterRequest request, String transactionId, Status status) {
        if ( request == null && transactionId == null && status == null ) {
            return null;
        }

        OrderEvent orderEvent = new OrderEvent();

        if ( request != null ) {
            orderEvent.setOrder( of( request.getOrder() ) );
            orderEvent.setInventory( of( request.getInventory() ) );
        }
        orderEvent.setTransactionId( transactionId );
        orderEvent.setStatus( status );

        return orderEvent;
    }
}
