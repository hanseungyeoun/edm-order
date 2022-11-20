package com.example.order.dto;

import com.example.order.constant.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderModelMapper {

    OrderModelMapper INSTANCE = Mappers.getMapper(OrderModelMapper.class);

    OrderEvent.Inventory of(OrderDto.Inventory inventory);

    OrderEvent.Order of(OrderDto.Order order);

/*
    @Mapping(target = "status", qualifiedByName = "getStatusName")
*/
    OrderEvent of(OrderDto.OrderRegisterRequest request, String transactionId, Status status);

}
