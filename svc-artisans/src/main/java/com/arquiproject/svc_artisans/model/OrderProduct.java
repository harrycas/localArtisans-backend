package com.arquiproject.svc_artisans.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orders_products")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Another Microservice
    @Column(name = "product_id", unique = true)
    private int productId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
