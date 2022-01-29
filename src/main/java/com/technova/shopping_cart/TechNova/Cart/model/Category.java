package com.technova.shopping_cart.TechNova.Cart.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "category_db")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long catId;

    @Column
    private String catName;

    @Column
    private String createdBy;
}
