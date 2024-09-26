package com.carrefour.kata.services.dto;

import com.carrefour.kata.domains.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;

    private CustomerDto customer;

    private OrderStatus status;

    private List<OrderItemDto> orderItems;

    private double totalPrice;

    private LocalDateTime orderDate;
}
