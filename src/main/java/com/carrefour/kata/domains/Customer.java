package com.carrefour.kata.domains;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
    private Cart cart;
}
