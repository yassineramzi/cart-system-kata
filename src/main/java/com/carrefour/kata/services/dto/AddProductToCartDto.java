package com.carrefour.kata.services.dto;

import lombok.Data;

@Data
public class AddProductToCartDto {
    private Long customerId;

    private Long productId;

    private int quantity;
}
