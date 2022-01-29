package com.technova.shopping_cart.TechNova.Cart.model;


import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "user_db")
@Data
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastname;

    @Column
    private String email;
}
