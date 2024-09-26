package com.carrefour.kata.services.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

import com.carrefour.kata.domains.Cart;
import com.carrefour.kata.domains.CartItem;
import com.carrefour.kata.domains.Customer;
import com.carrefour.kata.domains.Product;
import com.carrefour.kata.exceptions.CustomerNotFoundException;
import com.carrefour.kata.exceptions.InsufficientInventoryException;
import com.carrefour.kata.exceptions.ProductNotFoundException;
import com.carrefour.kata.repository.CartRepository;
import com.carrefour.kata.repository.CustomerRepository;
import com.carrefour.kata.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

public class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private Customer customer;
    private Product product;
    private Cart cart;

    @BeforeEach
    void setUp() {
        openMocks(this);

        customer = new Customer();
        customer.setId(1L);

        product = new Product();
        product.setId(1L);
        product.setInventoryCount(100);
        product.setName("Test Product");

        cart = new Cart();
        cart.setCustomer(customer);
    }

    @Test
    void addProductToCart_productAddedSuccessfully() {
        // Before
        Long customerId = 1L;
        Long productId = 1L;
        int quantity = 5;

        // When
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.findByCustomerId(customerId)).thenReturn(Optional.of(cart));

        cartService.addProductToCart(customerId, productId, quantity);

        // Then
        // Assert that the product inventory count was reduced
        assertEquals(95, product.getInventoryCount());

        // Verify that the repositories were called
        verify(cartRepository).save(any(Cart.class));
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void addProductToCart_productNotFound() {
        // Before
        Long customerId = 1L;
        Long productId = 999L;  // Non-existent product ID
        int quantity = 5;

        // When
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Then
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> cartService.addProductToCart(customerId, productId, quantity));

        assertEquals("Product not found with id 999", exception.getMessage());
    }

    @Test
    void addProductToCart_customerNotFound() {
        // Before
        Long customerId = 999L;  // Non-existent customer ID
        Long productId = 1L;
        int quantity = 5;

        // When
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Then
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                () -> cartService.addProductToCart(customerId, productId, quantity));

        assertEquals("Customer not found with id 999", exception.getMessage());
    }

    @Test
    void addProductToCart_insufficientInventory() {
        // Before
        Long customerId = 1L;
        Long productId = 1L;
        int quantity = 200;  // More than available inventory
        // When
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        // Then
        InsufficientInventoryException exception = assertThrows(InsufficientInventoryException.class,
                () -> cartService.addProductToCart(customerId, productId, quantity));

        assertEquals("Not enough inventory for product: Test Product", exception.getMessage());
    }

    @Test
    void addProductToCart_productAlreadyInCart() {
        // Before
        Long customerId = 1L;
        Long productId = 1L;
        int quantity = 5;

        CartItem existingCartItem = new CartItem(cart, product, 3);
        cart.getCartItems().add(existingCartItem);
        // When
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.findByCustomerId(customerId)).thenReturn(Optional.of(cart));

        cartService.addProductToCart(customerId, productId, quantity);
        // Then
        // Assert that the quantity of the existing cart item is updated
        assertEquals(8, existingCartItem.getQuantity());

        // Verify that the repositories were called
        verify(cartRepository).save(any(Cart.class));
        verify(productRepository).save(any(Product.class));
    }
}
