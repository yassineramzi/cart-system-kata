package com.carrefour.kata.services.mapper;

import com.carrefour.kata.domains.OrderItem;
import com.carrefour.kata.services.dto.OrderItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public non-sealed interface OrderItemMapper extends EntityMapper<OrderItemDto, OrderItem> {

    @Override
    OrderItemDto toDto(OrderItem orderItem);

    @Override
    OrderItem toEntity(OrderItemDto orderItemDto);

    default OrderItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        return orderItem;
    }
}
