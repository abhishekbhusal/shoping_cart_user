package com.technova.shopping_cart.TechNova.Cart.dto;

import lombok.Data;

@Data
public class OrderResponse {
    private Integer statusCode;
    private String message;
    private Object errors;
    private Object data;
}
