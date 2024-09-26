package com.carrefour.kata.services.impl;

import com.carrefour.kata.domains.*;
import com.carrefour.kata.exceptions.CartNotFoundException;
import com.carrefour.kata.exceptions.OrderNotFoundException;
import com.carrefour.kata.repository.CartRepository;
import com.carrefour.kata.repository.OrderRepository;
import com.carrefour.kata.services.OrderService;
import com.carrefour.kata.services.dto.OrderDto;
import com.carrefour.kata.services.mapper.OrderMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto confirmOrder(Long cartId) {
        log.info("Confirm order from cart : {}", cartId);
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id " + cartId));

        var order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());

        var orderItems = cart.getCartItems().stream().map(cartItem -> {
            var orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            return orderItem;
        }).toList();

        var totalPrice = cart.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();
        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        log.info("Get order by Id : {}", orderId);
        return this.orderRepository.findById(orderId)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + orderId));
    }
}
