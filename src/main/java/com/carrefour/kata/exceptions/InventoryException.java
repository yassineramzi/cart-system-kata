package com.carrefour.kata.exceptions;

public sealed class InventoryException extends RuntimeException
        permits ProductNotFoundException, InsufficientInventoryException {

    public InventoryException(String message) {
        super(message);
    }
}