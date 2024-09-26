package com.carrefour.kata.controllers;

import com.carrefour.kata.services.CartService;
import com.carrefour.kata.services.dto.AddProductToCartDto;
import com.carrefour.kata.services.dto.CartDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/carts")
@AllArgsConstructor
public class CartController {
    private final Logger log = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    @PostMapping("/add-product")
    public ResponseEntity<EntityModel<CartDto>> addProductToCart(
            @RequestBody AddProductToCartDto addProductToCartDto
    ) {
        log.info("Add Product : {} to cart of user : {} with quantity : {}", addProductToCartDto.getProductId(), addProductToCartDto.getCustomerId(), addProductToCartDto.getQuantity());
        cartService.addProductToCart(addProductToCartDto.getCustomerId(), addProductToCartDto.getProductId(), addProductToCartDto.getQuantity());

        CartDto cart = cartService.getCartByCustomerId(addProductToCartDto.getCustomerId());

        EntityModel<CartDto> cartModel = EntityModel.of(cart);

        cartModel.add(linkTo(methodOn(CartController.class).getCartByCustomer(addProductToCartDto.getCustomerId())).withSelfRel());

        return ResponseEntity.ok(cartModel);
    }

    @GetMapping("/{customerId}/customer")
    public ResponseEntity<EntityModel<CartDto>> getCartByCustomer(@PathVariable Long customerId) {
        log.info("Get Cart by customer Id : {}", customerId);
        CartDto cart = cartService.getCartByCustomerId(customerId);

        EntityModel<CartDto> cartModel = EntityModel.of(cart);

        cartModel.add(linkTo(methodOn(CartController.class).getCartByCustomer(customerId)).withSelfRel());

        return ResponseEntity.ok(cartModel);
    }

}