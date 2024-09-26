package com.carrefour.kata.services.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;

    private ProductDto product;

    private int quantity;
}
