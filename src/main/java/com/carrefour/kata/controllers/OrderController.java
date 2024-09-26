package com.carrefour.kata.controllers;

import com.carrefour.kata.services.OrderService;
import com.carrefour.kata.services.dto.AddProductToCartDto;
import com.carrefour.kata.services.dto.CartDto;
import com.carrefour.kata.services.dto.OrderDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class OrderController {

    private final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @PostMapping("/confirm/{cartId}/cart")
        public ResponseEntity<EntityModel<OrderDto>> confirmOrder(
            @PathVariable Long cartId
    ) {
        log.info("");
        OrderDto orderDto = orderService.confirmOrder(cartId);
        EntityModel<OrderDto> orderModel = EntityModel.of(orderDto);
        orderModel.add(linkTo(methodOn(OrderController.class).confirmOrder(cartId)).withSelfRel());
        orderModel.add(linkTo(methodOn(OrderController.class).getOrderById(orderDto.getId())).withRel("get_order_by_id"));
        return ResponseEntity.ok(orderModel);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<EntityModel<OrderDto>> getOrderById(@PathVariable Long orderId) {
        log.info("");
        OrderDto orderDto = orderService.getOrderById(orderId);
        EntityModel<OrderDto> orderModel = EntityModel.of(orderDto);
        orderModel.add(linkTo(methodOn(OrderController.class).getOrderById(orderDto.getId())).withSelfRel());
        return ResponseEntity.ok(orderModel);
    }
}
