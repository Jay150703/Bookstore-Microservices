package com.bookstore.feedback.controller;

import com.bookstore.feedback.entity.Review;
import com.bookstore.feedback.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Review> submit(@RequestHeader("userId") String userId,
                                         @RequestParam Long productId,
                                         @RequestParam String comment,
                                         @RequestParam int rating) {
        return ResponseEntity.ok(feedbackService.submitReview(userId, productId, comment, rating));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<Review>> getByProduct(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getReviewsByProduct(id));
    }

    @GetMapping("/product/{id}/rating")
    public ResponseEntity<Double> getRating(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getAverageRating(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> update(@PathVariable Long id,
                                         @RequestParam String comment,
                                         @RequestParam int rating) {
        return ResponseEntity.ok(feedbackService.updateReview(id, comment, rating));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        feedbackService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}