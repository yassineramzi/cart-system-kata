package com.carrefour.kata.domains;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;

    private Integer inventoryCount;  // Inventory tracking

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;
}

