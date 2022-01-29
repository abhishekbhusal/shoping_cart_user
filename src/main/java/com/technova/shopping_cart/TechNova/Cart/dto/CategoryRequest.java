package com.technova.shopping_cart.TechNova.Cart.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class CategoryRequest {
    @NotBlank
    private String catName;
    @NotBlank
    private String createdBy;
}

