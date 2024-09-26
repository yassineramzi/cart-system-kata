package com.carrefour.kata.controllers;

import com.carrefour.kata.services.ProductService;
import com.carrefour.kata.services.dto.CartDto;
import com.carrefour.kata.services.dto.ProductDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {
    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<EntityModel<ProductDto>> getProductById(@PathVariable Long productId) {
        log.info("Get Product by Id : {}", productId);
        ProductDto product = productService.getProductById(productId);
        EntityModel<ProductDto> cartModel = EntityModel.of(product);
        cartModel.add(linkTo(methodOn(ProductController.class).getProductById(productId)).withSelfRel());
        return ResponseEntity.ok(cartModel);
    }
}
