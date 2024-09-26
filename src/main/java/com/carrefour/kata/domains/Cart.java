package com.carrefour.kata.domains;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    // Method to calculate total price
    public double getTotalPrice() {
        return 0;
        //return cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
    }

}

