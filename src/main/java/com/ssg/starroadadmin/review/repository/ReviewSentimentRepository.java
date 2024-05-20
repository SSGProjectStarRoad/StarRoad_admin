package com.ssg.starroadadmin.review.repository;

import com.ssg.starroadadmin.review.entity.ReviewSentiment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewSentimentRepository extends JpaRepository<ReviewSentiment, Long> {
    Optional<List<ReviewSentiment>> findAllByReviewId(Long reviewId);
}
