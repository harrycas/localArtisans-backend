package com.arquiproject.svc_artisans.model;

import com.arquiproject.svc_artisans.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    // To be implemented in the 2nd Sprint
    //private LocalDateTime shipDate;       // When the delivery pick up the product from the artesan

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;  // When the product would arrive

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "delivery_city")
    private String deliveryCity;

    @Column(name = "delivery_postal_code")
    private String deliveryPostalCode;

    @Column(name = "delivery_price")
    private double deliveryPrice;

    private double total;
    private double discount;  // in terms of %

    @Column(name = "is_cart")
    private boolean isCart;     // Identify the order as temporal & not final

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderProduct> orderProducts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private User deliveryPerson; // Repartidor asignado a la orden

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    @Enumerated(EnumType.STRING)  // Save the Enum as a String in the Database
    private OrderStatus status = OrderStatus.REQUEST_RECEIVED;  // Initial state
}
