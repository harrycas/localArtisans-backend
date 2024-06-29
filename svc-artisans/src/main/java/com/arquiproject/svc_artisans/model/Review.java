package com.arquiproject.svc_artisans.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId;

    private int rate;

    private String comment;

    @Column(name = "date_review")
    private LocalDate dateReview;

    //Another Microservice
    @Column(name = "product_id")
    private int productId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
