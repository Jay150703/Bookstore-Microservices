package com.bookstore.feedback.service;

import com.bookstore.feedback.entity.Review;
import com.bookstore.feedback.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final ReviewRepository reviewRepository;

    public Review submitReview(String userId, Long productId, String comment, int rating) {
        return reviewRepository.save(Review.builder()
                .productId(productId).userId(userId).comment(comment).rating(rating).build());
    }

    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public double getAverageRating(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        OptionalDouble avg = reviews.stream().mapToInt(Review::getRating).average();
        return avg.orElse(0.0);
    }

    public Review updateReview(Long id, String comment, int rating) {
        Review r = reviewRepository.findById(id).orElseThrow();
        r.setComment(comment);
        r.setRating(rating);
        return reviewRepository.save(r);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}