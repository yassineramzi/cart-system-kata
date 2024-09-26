package com.carrefour.kata.services.mapper;

import com.carrefour.kata.domains.Order;
import com.carrefour.kata.services.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public non-sealed interface OrderMapper extends EntityMapper<OrderDto, Order> {

    @Override
    OrderDto toDto(Order order);

    @Override
    Order toEntity(OrderDto orderDto);

    default Order fromId(Long id) {
        if (id == null) {
            return null;
        }
        Order order = new Order();
        order.setId(id);
        return order;
    }
}
