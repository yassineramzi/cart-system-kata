package com.carrefour.kata.services.dto;

import lombok.Data;

@Data
public class CartItemDto {

    private Long id;

    private ProductDto product;

    private int quantity;
}
