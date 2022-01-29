package com.technova.shopping_cart.TechNova.Cart.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRequest {
    @NotBlank(message = "Name should not be empty")
    private String firstName;

    @NotBlank(message = "Lastname should not be empty")
    private String lastName;

    @NotBlank(message = "Email should not be empty")
    @Email
    private String email;
}
