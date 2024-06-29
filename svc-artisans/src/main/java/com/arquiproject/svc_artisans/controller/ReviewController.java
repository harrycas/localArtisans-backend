package com.arquiproject.svc_artisans.controller;

import com.arquiproject.svc_artisans.model.Review;
import com.arquiproject.svc_artisans.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@CrossOrigin("*")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService=reviewService;
    }

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody Review review){
        Review createdReview = reviewService.createReview(review);
        if(createdReview != null){
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> deleteById(@PathVariable int reviewId){
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<Review> updateReview(@RequestBody Review review){
        Review updatedReview = reviewService.updateReview(review);
        if (updatedReview != null){
            return new ResponseEntity<>(review, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
