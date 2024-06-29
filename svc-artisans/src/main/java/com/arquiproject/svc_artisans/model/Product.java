package com.arquiproject.svc_artisans.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product {

    private int productId;
    private String name;
    private String description;
    private String image;
    private double price;
    private boolean active;
    private int userId;
    private int categoryId;

}
