package com.arquiproject.msvc_catalog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private boolean active;

    // Like a Foreign Key of User(From svc-artisans). Pass through the session ID
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}