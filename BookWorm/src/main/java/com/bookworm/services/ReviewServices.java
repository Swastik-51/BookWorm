package com.bookworm.services;


import com.bookworm.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewServices {
    List<Review> getAllReviews();
    Optional<Review> getReviewById(Long id);
    Review saveReview(Review review);
    void deleteReview(Long id);
}
