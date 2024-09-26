package com.carrefour.kata.exceptions;

public final class InsufficientInventoryException extends InventoryException{

    public InsufficientInventoryException(String message) {
        super(message);
    }

}
