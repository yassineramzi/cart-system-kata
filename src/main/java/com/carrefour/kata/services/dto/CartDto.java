package com.carrefour.kata.services.dto;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class CartDto {

    private Long id;

    private CustomerDto customer;

    private List<CartItemDto> cartItems = new ArrayList<>();
}
