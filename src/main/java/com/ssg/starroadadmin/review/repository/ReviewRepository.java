package com.ssg.starroadadmin.review.repository;

import com.ssg.starroadadmin.review.dto.RecentReviewResponse;
import com.ssg.starroadadmin.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findAllByStoreId(Long storeId);

    Long countByUserId(Long id);

    Optional<List<Review>> findTop5ByOrderByCreatedAtDesc();
}
