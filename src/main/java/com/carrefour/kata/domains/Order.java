package com.carrefour.kata.domains;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // Enum to track status (PENDING, SHIPPED, DELIVERED, etc.)

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    private double totalPrice;

    private LocalDateTime orderDate;

}
