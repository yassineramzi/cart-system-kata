package com.carrefour.kata.services;

import com.carrefour.kata.services.dto.OrderDto;

public interface OrderService {
    OrderDto confirmOrder(Long cartId);

    OrderDto getOrderById(Long orderId);
}
