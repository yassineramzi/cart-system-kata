package com.carrefour.kata.services;

import com.carrefour.kata.services.dto.CartDto;

public interface CartService {

    void addProductToCart(Long customerId, Long productId, int quantity);

    CartDto getCartByCustomerId(Long customerId);

    void removeProductFromCart(Long customerId, Long productId);
}
