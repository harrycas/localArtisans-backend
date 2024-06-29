package com.arquiproject.msvc_catalog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    private String name;
    private String description;
    private String image;
    private double price;
    private boolean active;

    //Like a Foreign Key of User
    @Column(name = "user_id")
    private int userId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}