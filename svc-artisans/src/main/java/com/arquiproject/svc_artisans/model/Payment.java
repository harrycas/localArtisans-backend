package com.arquiproject.svc_artisans.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_email")
    private String userEmail;
    private double amount;

    /*@Column(name = "card_holder")
    private String cardHolder;

    @Column(name = "card_number")
    private String cardNumber;

    private String cvv;

    @Column(name = "billing_address")
    private String billingAddress;

    private String type;*/

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
