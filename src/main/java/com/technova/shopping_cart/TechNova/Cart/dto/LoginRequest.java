package com.technova.shopping_cart.TechNova.Cart.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "Email should not be empty")
    private  String email;

    @NotBlank(message = "Password should not be empty")
    private String password;
}
