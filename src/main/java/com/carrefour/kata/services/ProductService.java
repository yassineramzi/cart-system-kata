package com.carrefour.kata.services;

import com.carrefour.kata.services.dto.ProductDto;

public interface ProductService {
    ProductDto getProductById(Long productId);
}
