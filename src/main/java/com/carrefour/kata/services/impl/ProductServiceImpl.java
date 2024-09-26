package com.carrefour.kata.services.impl;

import com.carrefour.kata.exceptions.ProductNotFoundException;
import com.carrefour.kata.repository.ProductRepository;
import com.carrefour.kata.services.ProductService;
import com.carrefour.kata.services.dto.ProductDto;
import com.carrefour.kata.services.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public ProductDto getProductById(Long productId) {
        log.info("Get product by id : {}", productId);
        return this.productRepository.findById(productId)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));
    }
}
