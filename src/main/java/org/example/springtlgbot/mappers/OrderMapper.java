package org.example.springtlgbot.mappers;

import org.example.springtlgbot.dto.OrderDTO;
import org.example.springtlgbot.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderDTO orderDto);
}
