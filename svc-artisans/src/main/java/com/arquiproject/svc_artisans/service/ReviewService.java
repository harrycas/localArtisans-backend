package com.arquiproject.svc_artisans.service;

import com.arquiproject.svc_artisans.model.Review;
import com.arquiproject.svc_artisans.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService implements IReviewService{

    final private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public boolean deleteReview(int reviewId) {
        boolean deleted = false;
        try{
            reviewRepository.deleteById(reviewId);
            deleted = true;
        } catch (Exception e) {
            System.out.println("Error al eliminar el siguiente review : "+reviewId+ e.getMessage());
        }
        return deleted;
    }

    @Override
    public Review updateReview(Review review) {
        Review foundReview = reviewRepository.findById(review.getReviewId()).orElse(null);
        if(foundReview != null){
            return reviewRepository.save(review);
        } else {
            return null;
        }
    }
}
