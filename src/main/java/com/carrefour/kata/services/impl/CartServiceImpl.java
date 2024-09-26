package com.carrefour.kata.services.impl;

import com.carrefour.kata.domains.*;
import com.carrefour.kata.exceptions.CustomerNotFoundException;
import com.carrefour.kata.exceptions.InsufficientInventoryException;
import com.carrefour.kata.exceptions.ProductNotFoundException;
import com.carrefour.kata.repository.CartRepository;
import com.carrefour.kata.repository.CustomerRepository;
import com.carrefour.kata.repository.ProductRepository;
import com.carrefour.kata.services.CartService;

import com.carrefour.kata.services.dto.CartDto;
import com.carrefour.kata.services.mapper.CartMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    private final CartMapper cartMapper;

    @Override
    @Transactional
    public void addProductToCart(Long customerId, Long productId, int quantity) {
        log.info("Add Product : {} to cart of user : {} with quantity : {}", productId, customerId, quantity);
        var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + customerId));

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));

        if (product.getInventoryCount() < quantity) {
            throw new InsufficientInventoryException("Not enough inventory for product: " + product.getName());
        }

        var cart = cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> cartRepository.save(this.createNewCartForCustomer(customer)));

        cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        existingItem -> existingItem.setQuantity(existingItem.getQuantity() + quantity),
                        () -> cart.getCartItems().add(new CartItem(cart, product, quantity))
                );
        int newProductQuantityInInventory = product.getInventoryCount() - quantity;
        log.info("Update Product : {} in inventory with new quantity : {}", product.getName(), newProductQuantityInInventory);
        product.setInventoryCount(newProductQuantityInInventory);
        productRepository.save(product);
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByCustomerId(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + customerId));
        return cartMapper.toDto(cart);
    }

    private Cart createNewCartForCustomer(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        return cart;
    }
}

