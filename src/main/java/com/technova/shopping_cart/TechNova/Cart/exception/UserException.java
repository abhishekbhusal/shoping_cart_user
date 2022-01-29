package com.technova.shopping_cart.TechNova.Cart.exception;

public class UserException extends RuntimeException{
    private String message;

    public UserException(String message) {
        super(message);
        this.message = message;
    }
}
