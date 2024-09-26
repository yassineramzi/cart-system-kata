package com.carrefour.kata.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.carrefour.kata.domains.Cart;
import com.carrefour.kata.domains.CartItem;
import com.carrefour.kata.domains.Product;
import com.carrefour.kata.exceptions.CartNotFoundException;
import com.carrefour.kata.exceptions.CustomerNotFoundException;
import com.carrefour.kata.exceptions.ProductNotInCartException;
import com.carrefour.kata.repository.CartRepository;
import com.carrefour.kata.repository.CustomerRepository;
import com.carrefour.kata.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

public class RemoveItemFromCartTestCase {

        @InjectMocks
        private CartServiceImpl cartService;

        @Mock
        private CustomerRepository customerRepository;

        @Mock
        private CartRepository cartRepository;

        @Mock
        private ProductRepository productRepository;

        private Long customerId;
        private Long productId;
        private Cart cart;
        private Product product;

    @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
            customerId = 1L;
            productId = 1L;

            product = new Product();
            product.setId(productId);
            product.setInventoryCount(10);

            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(2);

            cart = new Cart();
            cart.setCartItems(new ArrayList<>());
            cart.getCartItems().add(cartItem);
        }

        @Test
        public void testRemoveProductFromCart_Success() {
            when(customerRepository.existsById(customerId)).thenReturn(true);
            when(cartRepository.findByCustomerId(customerId)).thenReturn(Optional.of(cart));

            cartService.removeProductFromCart(customerId, productId);

            assertEquals(12, product.getInventoryCount()); // Inventory should increase by quantity of cart item
            assertTrue(cart.getCartItems().isEmpty()); // Cart should be empty after removal
            verify(productRepository).save(product); // Verify that product was saved
            verify(cartRepository).save(cart); // Verify that cart was saved
        }

        @Test
        public void testRemoveProductFromCart_ProductNotFoundInCart() {
            when(customerRepository.existsById(customerId)).thenReturn(true);
            when(cartRepository.findByCustomerId(customerId)).thenReturn(Optional.of(cart));

            // Attempt to remove a product that does not exist in the cart
            assertThrows(ProductNotInCartException.class, () -> {
                cartService.removeProductFromCart(customerId, 999L);
            });
        }

        @Test
        public void testRemoveProductFromCart_CustomerNotFound() {
            when(customerRepository.existsById(customerId)).thenReturn(false);

            // Attempt to remove product when customer does not exist
            assertThrows(CustomerNotFoundException.class, () -> {
                cartService.removeProductFromCart(customerId, productId);
            });
        }

        @Test
        public void testRemoveProductFromCart_CartNotFound() {
            when(customerRepository.existsById(customerId)).thenReturn(true);
            when(cartRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

            // Attempt to remove product when cart is not found
            assertThrows(CartNotFoundException.class, () -> {
                cartService.removeProductFromCart(customerId, productId);
            });
        }
}
