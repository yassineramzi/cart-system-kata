package com.carrefour.kata.exceptions;

public final class ProductNotFoundException extends InventoryException {

    public ProductNotFoundException(String message) {
        super(message);
    }

}