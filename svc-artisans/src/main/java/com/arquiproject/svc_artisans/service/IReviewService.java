package com.arquiproject.svc_artisans.service;

import com.arquiproject.svc_artisans.model.Review;

public interface IReviewService {
    Review createReview(Review review);
    boolean deleteReview (Long reviewId);
    Review updateReview(Review review);

}
